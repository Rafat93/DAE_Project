package ws;



import dtos.TreinadorDTO;
import dtos.ModalidadeDTO;
import dtos.TreinoDTO;
import ejbs.TreinadorBean;
import entities.*;
import entities.Treinador;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/treinadores") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TreinadorController {
    @EJB
    private TreinadorBean treinadorBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    @RolesAllowed({"Administrador"})
    public List<TreinadorDTO> all() {
        return toDTOsNoModalidades(treinadorBean.all());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrador"})
    public Response createNewTreinador (TreinadorDTO treinadorDTO){
        Treinador treinador = treinadorBean.create(
                treinadorDTO.getNome(),
                treinadorDTO.getPassword(),
                treinadorDTO.getEmail(),
                treinadorDTO.getNumeroCedula()
        );
        return Response.status(Response.Status.CREATED).entity(toDTO(treinador)).build();
    }

    @PUT
    @Path("{email}")
    public Response update(@PathParam("email") String email, TreinadorDTO treinadorDTO)
            throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        System.out.println(email + " --- " + principal.getName());

        if(securityContext.isUserInRole("Administrador") ||
                securityContext.isUserInRole("Treinador") && principal.getName().equals(treinadorDTO.getNome())) {

            treinadorBean.update(
                    treinadorDTO.getNome(),
                    treinadorDTO.getPassword(),
                    email,
                    treinadorDTO.getNumeroCedula());

            Treinador treinador = treinadorBean.findTreinador(email);
            return Response.status(Response.Status.OK)
                    .entity(toDTO(treinador))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("{email}")
    @RolesAllowed({"Administrador"})
    public Response removeTreinador(@PathParam("email") String email)
            throws MyEntityNotFoundException {
        treinadorBean.delete(email);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{email}")
    public Response getTreinadorDetails (@PathParam("email") String email) throws MyEntityAlreadyExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Principal principal = securityContext.getUserPrincipal();
        System.out.println(email + " --- " + principal.getName());
        Treinador treinador = treinadorBean.findTreinador(email);
        if(securityContext.isUserInRole("Administrador") ||
                securityContext.isUserInRole("Treinador") && principal.getName().equals(treinador.getNome())) {
            return Response.status(Response.Status.OK)
                    .entity(toDTO(treinador))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{email}/modalidades")
    public Response getTreinadorModalidades(@PathParam("email") String email){
        Treinador treinador = treinadorBean.findTreinador(email);
        if (treinador != null){
            GenericEntity<List<ModalidadeDTO>> entity
                    = new GenericEntity<List<ModalidadeDTO>>(modalidadeToDTOs(treinador.getModalidades())){

            };
            return  Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Treinador with email "+email+ " not found")
                .build();
    }

    @GET
    @Path("{email}/treinos")
    public Response getTreinadorTreinos(@PathParam("email") String email){
        Treinador treinador = treinadorBean.findTreinador(email);
        if (treinador != null){
            GenericEntity<List<TreinoDTO>> entity
                    = new GenericEntity<List<TreinoDTO>>(treinoToDTOs(treinador.getTreinosLecionados())){
            };
            return  Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Treinador with email "+email+ " not found")
                .build();
    }

    @PUT
    @Path("{email}/modalidade/enroll/{sigla}")
    @RolesAllowed({"Administrador"})
    public Response enrollTreinadorInModalidade(@PathParam("email") String email, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        treinadorBean.enrollTreinadorInModalidade(email, sigla);
        return getTreinadorModalidades(email);
    }

    @PUT
    @Path("{email}/modalidade/unroll/{sigla}")
    @RolesAllowed({"Administrador"})
    public Response unrollTreinadorFromModalidade(@PathParam("email") String email, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        treinadorBean.unrollTreinadorFromModalidade(email, sigla);
        return getTreinadorModalidades(email);
    }

    @PUT
    @Path("{email}/treino/enroll/{code}")
    @RolesAllowed({"Administrador"})
    public Response enrollTreinadorInTreino(@PathParam("email") String email, @PathParam("code") int code)throws MyEntityNotFoundException, MyIllegalArgumentException {
        treinadorBean.enrollTreinadorInTreino(email, code);
        return getTreinadorTreinos(email);
    }

    @PUT
    @Path("{email}/modalidade/unroll/{code}")
    @RolesAllowed({"Administrador"})
    public Response unrollTreinadorInTreino(@PathParam("email") String email, @PathParam("code") int code)throws MyEntityNotFoundException, MyIllegalArgumentException {
        treinadorBean.unrollTreinadorFromTreino(email, code);
        return getTreinadorTreinos(email);
    }

    @GET
    @Path("{email}/modalidades_livres")
    @RolesAllowed({"Administrador"})
    public Response getModalidadesLivres(@PathParam("email")String email) throws MyEntityNotFoundException {
        List <Modalidade> modalidades = treinadorBean.getModalidadesSemTreinador(email);
        return  Response.status(Response.Status.OK)
                .entity(modalidadeToDTOs(modalidades))
                .build();
    }

    TreinadorDTO toDTO(Treinador treinador){
        TreinadorDTO treinadorDTO = new TreinadorDTO(
                treinador.getNumeroCedula(),
                treinador.getNome(),
                treinador.getEmail(),
                treinador.getPassword()
        );

        List <ModalidadeDTO> modalidadeDTOS = modalidadeToDTOs(treinador.getModalidades());
        treinadorDTO.setModalidades(modalidadeDTOS);

        return treinadorDTO;
    }

    TreinadorDTO toDTONoModalidades(Treinador treinador){
        return new TreinadorDTO(
                treinador.getNumeroCedula(),
                treinador.getNome(),
                treinador.getEmail(),
                treinador.getPassword()
        );
    }

    List<TreinadorDTO> toDTOsNoModalidades (List<Treinador> treinador){
        return treinador.stream().map(this::toDTONoModalidades).collect(Collectors.toList());
    }

    ModalidadeDTO modalidadeToDTO(Modalidade modalidade){
        return new ModalidadeDTO(modalidade.getSigla(),
                modalidade.getNome(),
                modalidade.getEpocaDesportiva());
    }

    List<ModalidadeDTO> modalidadeToDTOs(Collection<Modalidade> modalidades){
        return modalidades.stream().map(this::modalidadeToDTO).collect(Collectors.toList());
    }

    TreinoDTO treinoToDTO (Treino treino){
        return new TreinoDTO(
                treino.getCode(),
                treino.getTreinador().getNome(),
                treino.getModalidade().getSigla(),
                treino.getGraduacao().getCode(),
                treino.getEscalao().getCode(),
                treino.getHoraInicio(),
                treino.getHoraFim(),
                treino.getDiaSemana()
        );
    }

    List<TreinoDTO> treinoToDTOs(Collection<Treino> treinos){
        return treinos.stream().map(this::treinoToDTO).collect(Collectors.toList());
    }

}
