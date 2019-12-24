package ws;


import dtos.ModalidadeDTO;
import dtos.SocioDTO;
import ejbs.SocioBean;
import entities.Modalidade;
import entities.Socio;
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

@Path("/socios") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SocioController {

    @EJB
    private SocioBean socioBean;

    @Context
    private SecurityContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/atletas/”
    @RolesAllowed({"Administrator"})
    public List<SocioDTO> all() {
        return toDTOsNoModalidades(socioBean.all());
    }

    @POST
    @Path("/")
    public Response createNewSocio (SocioDTO socioDTO) throws MyEntityAlreadyExistsException, MyConstraintViolationException, MyIllegalArgumentException {
        Socio socio = socioBean.create(
                socioDTO.getNumeroSocio(),
                socioDTO.getNome(),
                socioDTO.getEmail(),
                socioDTO.getPassword(),
                socioDTO.getDataNascimento().get(Calendar.DAY_OF_MONTH),
                socioDTO.getDataNascimento().get(Calendar.MONTH),
                socioDTO.getDataNascimento().get(Calendar.YEAR));
        return Response.status(Response.Status.CREATED).entity(socioToDTO(socio)).build();
    }

    @PUT
    @Path("{email}")
    public Response updateSocio (@PathParam("email") String email,SocioDTO socioDTO) throws MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException, MyEntityAlreadyExistsException {
        socioBean.update(socioDTO.getNumeroSocio(),
                socioDTO.getNome(),
                socioDTO.getEmail(),
                socioDTO.getPassword(),
                socioDTO.getDataNascimento().get(Calendar.DAY_OF_MONTH),
                socioDTO.getDataNascimento().get(Calendar.MONTH),
                socioDTO.getDataNascimento().get(Calendar.YEAR));
        Socio socio = socioBean.findSocio(email);
        return Response.status(Response.Status.OK)
                .entity(socioToDTO(socio))
                .build();
    }

    @DELETE
    @Path("{email}")
    public Response removeSocio(@PathParam("email") String email)
            throws MyEntityNotFoundException {
        socioBean.delete(email);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{email}")
    public Response getSociosDetails(@PathParam("email")String email) throws MyEntityAlreadyExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Principal principal = securityContext.getUserPrincipal();
        System.out.println(email + " --- " + principal.getName());

        if(securityContext.isUserInRole("Administrador") ||
        securityContext.isUserInRole("Atleta") && principal.getName().equals(email)) {
            Socio socio = socioBean.findSocio(email);
            return Response.status(Response.Status.OK)
                    .entity(socioToDTO(socio))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{email}/modalidades_subscritas")
    public Response getSocioModalidadesSubscritas(@PathParam("email") String email){
        Socio socio = socioBean.findSocio(email);
        if (socio != null){
            GenericEntity<List<ModalidadeDTO>> entity
                    = new GenericEntity<List<ModalidadeDTO>>(modalidadeToDTOs(socio.getModalidades())){

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
    @Path("{email}/modalidade/subscribe/{sigla}")
    public Response subscribeAtletaInModalidae(@PathParam("email") String email, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        socioBean.subscribeModalidade(email, sigla);
        return getSocioModalidadesSubscritas(email);
    }



    // Converts an entity Atleta to a DTO Atleta class
    SocioDTO socioToDTO(Socio socio){
        SocioDTO socioDTO = new SocioDTO(
                socio.getNumeroSocio(),
                socio.getNome(),
                socio.getEmail(),
                socio.getPassword(),
                socio.getDataNascimento()
        );

        List <ModalidadeDTO> modalidadesDTO = modalidadeToDTOs(socio.getModalidades());
        socioDTO.setModalidades(modalidadesDTO);

        return socioDTO;
    }

    SocioDTO toDTONoModalidades (Socio socio){
        return new SocioDTO(
                socio.getNumeroSocio(),
                socio.getNome(),
                socio.getEmail(),
                socio.getPassword(),
                socio.getDataNascimento()
        );
    }

    List<SocioDTO> toDTOsNoModalidades(List<Socio> socios) {
        return socios.stream().map(this::toDTONoModalidades).collect(Collectors.toList());

    }

    ModalidadeDTO socioToDTO(Modalidade modalidade){
        return new ModalidadeDTO(modalidade.getSigla(),
                modalidade.getNome(),
                modalidade.getTreinos(),
                modalidade.getEscaloes(),
                modalidade.getAtletas(),
                modalidade.getSocios());
    }

    List<ModalidadeDTO> modalidadeToDTOs(Collection<Modalidade> modalidades){
        return modalidades.stream().map(this::socioToDTO).collect(Collectors.toList());
    }
}
