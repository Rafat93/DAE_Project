package ws;

import dtos.ModalidadeDTO;
import dtos.TreinoDTO;
import ejbs.TreinoBean;
import entities.Modalidade;
import entities.Treino;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Path("/treinos") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TreinoController {

    @EJB
    private TreinoBean treinoBean;
    @Context
    private SecurityContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/treinos/”
    public List<TreinoDTO> all(){
        return toDTOs(treinoBean.all());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewTreino(TreinoDTO treinoDTO){
        Principal principal = securityContext.getUserPrincipal();
        System.out.println("Treino: " + treinoDTO.getCode() + " --- " + principal.getName());

        if(securityContext.isUserInRole("Administrador")) {
            Treino treino = treinoBean.create(
                    treinoDTO.getCode(),
                    treinoDTO.getEmailTreinador(),
                    treinoDTO.getSiglaModalidade(),
                    treinoDTO.getCodeGraduacao(),
                    treinoDTO.getCodeEscalao(),
                    treinoDTO.getHoraInicio(),
                    treinoDTO.getHoraFim(),
                    treinoDTO.getDiaSemana()
            );
            return Response.status(Response.Status.CREATED).entity(toDTO(treino)).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("{code}")
    @RolesAllowed({"Administrator"})
    public Response updateTreino(@PathParam("code") String code, TreinoDTO treinoDTO) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        System.out.println("Treino: " + treinoDTO.getCode() + " --- " + principal.getName());
        if(securityContext.isUserInRole("Administrador")) {
            treinoBean.update(
                    code,
                    treinoDTO.getEmailTreinador(),
                    treinoDTO.getSiglaModalidade(),
                    treinoDTO.getCodeGraduacao(),
                    treinoDTO.getCodeEscalao(),
                    treinoDTO.getHoraInicio(),
                    treinoDTO.getHoraFim(),
                    treinoDTO.getDiaSemana()
            );
            Treino treino = treinoBean.findTreino(code);
            return Response.status(Response.Status.OK)
                    .entity(toDTO(treino))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("{code}")
    @RolesAllowed({"Administrator"})
    public Response removeTreino (@PathParam("code") String code){
        treinoBean.delete(code);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{code}")
    public Response getTreinoDetails (@PathParam("code") String code){
        Principal principal = securityContext.getUserPrincipal();
        System.out.println("Treino: "+ code + " --- " + principal.getName());

        if(securityContext.isUserInRole("Administrador") ||
                securityContext.isUserInRole("Treinador") ){
            Treino treino = treinoBean.findTreino(code);
            return Response.status(Response.Status.OK)
                    .entity(toDTO(treino))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    @Path("{code}/modalidade/enroll/{sigla}")
    @RolesAllowed({"Administrator"})
    public Response enrollAtletaInModalidade(@PathParam("code") String code, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        treinoBean.enrollTreinoInModalidade(code, sigla);
        return getModalidade(code);
    }

    @PUT
    @Path("{code}/modalidade/unroll/{sigla}")
    @RolesAllowed({"Administrator"})
    public Response unrollTreinoFromModalidade(@PathParam("code") String code, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        treinoBean.unrollTreinoFromModalidade(code, sigla);
        return getModalidade(code);
    }

    @GET
    @Path("{code}/modalidade")
    public Response getModalidade(@PathParam("code") String code){
        Treino treino = treinoBean.findTreino(code);
        if(treino != null){
            return Response.status(Response.Status.OK)
                    .entity(treinoToDTO(treino.getModalidade()))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Treino com o codigo "+code+" não encontrado")
                .build();
    }

    TreinoDTO toDTO (Treino treino){
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

    List <TreinoDTO> toDTOs (List <Treino> treinos){
        return treinos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    ModalidadeDTO treinoToDTO(Modalidade modalidade){
        return new ModalidadeDTO(modalidade.getSigla(),
                modalidade.getNome(),
                modalidade.getEpocaDesportiva());
    }
}
