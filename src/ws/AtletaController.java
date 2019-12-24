package ws;

import dtos.AtletaDTO;
import dtos.ModalidadeDTO;
import ejbs.AtletaBean;
import entities.Atleta;
import entities.Modalidade;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/atletas") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class AtletaController {

    @EJB
    private AtletaBean atletaBean;

    @Context
    private SecurityContext securityContext;


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/atletas/”
    @RolesAllowed({"Administrator"})
    public List<AtletaDTO> all() {
        return toDTOsNoModalidades(atletaBean.all());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewAtleta (AtletaDTO atletaDTO) {
        Atleta atleta = atletaBean.create(
                atletaDTO.getNumeroSocio(),
                atletaDTO.getNome(),
                atletaDTO.getEmail(),
                atletaDTO.getPassword(),
                atletaDTO.getDataNascimento().get(Calendar.DAY_OF_MONTH),
                atletaDTO.getDataNascimento().get(Calendar.MONTH),
                atletaDTO.getDataNascimento().get(Calendar.YEAR));
        return Response.status(Response.Status.CREATED).entity(atletaToDTO(atleta)).build();
    }

    @PUT
    @Path("{email}")
    public Response updateAtleta(@PathParam("email") String email, AtletaDTO atletaDTO)
            throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        System.out.println(email + " --- " + principal.getName());

        if(securityContext.isUserInRole("Administrador") ||
                securityContext.isUserInRole("Atleta") && principal.getName().equals(atletaDTO.getNome())) {

            atletaBean.update(atletaDTO.getNumeroSocio(),
                    atletaDTO.getNome(),
                    email,
                    atletaDTO.getPassword(),
                    atletaDTO.getDataNascimento().get(Calendar.DAY_OF_MONTH),
                    atletaDTO.getDataNascimento().get(Calendar.MONTH),
                    atletaDTO.getDataNascimento().get(Calendar.YEAR));
            Atleta atleta = atletaBean.findAtleta(email);
            return Response.status(Response.Status.OK)
                    .entity(atletaToDTO(atleta))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("{email}")
    @RolesAllowed({"Administrator"})
    public Response removeAtleta(@PathParam("email") String email)
            throws MyEntityNotFoundException {
        atletaBean.delete(email);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{email}")
    public Response getAtletaDetails (@PathParam("email")String email) throws MyEntityAlreadyExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Principal principal = securityContext.getUserPrincipal();
        System.out.println(email + " --- " + principal.getName());

        Atleta atleta = atletaBean.findAtleta(email);
        if(securityContext.isUserInRole("Administrador") ||
                securityContext.isUserInRole("Atleta") && principal.getName().equals(atleta.getNome())) {

            return Response.status(Response.Status.OK)
                    .entity(atletaToDTO(atleta))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{email}/modalidades")
    public Response getAtletaModalidades(@PathParam("email") String email){
        Atleta atleta = atletaBean.findAtleta(email);
        if (atleta != null){
            GenericEntity<List<ModalidadeDTO>> entity
                    = new GenericEntity<List<ModalidadeDTO>>(modalidadeToDTOs(atleta.getModalidades())){

            };
            return  Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Atleta with email "+email+ " not found")
                .build();
    }

    @PUT
    @Path("{email}/modalidade/enroll/{sigla}")
    @RolesAllowed({"Administrator"})
    public Response enrollAtletaInModalidade(@PathParam("email") String email, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        atletaBean.enrollAtletaInModalidade(email, sigla);
        return getAtletaModalidades(email);
    }

    @PUT
    @Path("{email}/modalidade/unroll/{sigla}")
    @RolesAllowed({"Administrator"})
    public Response unrollAtletaFromModalidade(@PathParam("email") String email, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        atletaBean.unrollAtletaFromModalidade(email, sigla);
        return getAtletaModalidades(email);
    }


    // Converts an entity Atleta to a DTO Atleta class
    AtletaDTO atletaToDTO(Atleta atleta){
        AtletaDTO atletaDTO = new AtletaDTO(
                atleta.getNumeroSocio(),
                atleta.getNome(),
                atleta.getEmail(),
                atleta.getPassword(),
                atleta.getDataNascimento()
        );

        List < ModalidadeDTO> modalidadesDTO = modalidadeToDTOs(atleta.getModalidades());
        atletaDTO.setModalidades(modalidadesDTO);

        return atletaDTO;
    }

    AtletaDTO toDTONoModalidades(Atleta atleta){
        return new AtletaDTO(
                atleta.getNumeroSocio(),
                atleta.getNome(),
                atleta.getEmail(),
                atleta.getPassword(),
                atleta.getDataNascimento()
        );
    }


    ModalidadeDTO atletaToDTO(Modalidade modalidade){
        return new ModalidadeDTO(modalidade.getSigla(),
                modalidade.getNome(),
                modalidade.getEpocaDesportiva());
    }

    // converts an entire list of Atletas entities without the list of Modalidades into a list of DTOs
    List <AtletaDTO> toDTOsNoModalidades (List<Atleta> atletas){
        return atletas.stream().map(this::toDTONoModalidades).collect(Collectors.toList());
    }

    List<ModalidadeDTO> modalidadeToDTOs(Collection<Modalidade> modalidades){
        return modalidades.stream().map(this::atletaToDTO).collect(Collectors.toList());
    }

}
