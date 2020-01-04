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
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static utils.Utilitarios.format;

@Path("/socios") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SocioController {

    @EJB
    private SocioBean socioBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    @RolesAllowed({"Administrador"})
    public List<SocioDTO> all() {
        return toDTOsNoModalidades(socioBean.all());
    }

    @POST
    @Path("/")
    public Response createNewSocio (SocioDTO socioDTO) throws MyEntityAlreadyExistsException, MyConstraintViolationException, MyIllegalArgumentException, ParseException {
        GregorianCalendar dataNascimento = format(socioDTO.getDataNascimento());
        Socio socio = socioBean.create(
                socioDTO.getNumeroSocio(),
                socioDTO.getNome(),
                socioDTO.getPassword(),
                socioDTO.getEmail(),
                dataNascimento.get(Calendar.DAY_OF_MONTH),
                dataNascimento.get(Calendar.MONTH),
                dataNascimento.get(Calendar.YEAR),
                socioDTO.getNumIdentificacaoCivil(),
                socioDTO.getNumContribuinte(),
                socioDTO.getMorada());
        return Response.status(Response.Status.CREATED).entity(socioToDTO(socio)).build();
    }

    @PUT
    @Path("{email}")
    public Response updateSocio (@PathParam("email") String email,SocioDTO socioDTO) throws MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException, MyEntityAlreadyExistsException, ParseException {
        GregorianCalendar dataNascimento = format(socioDTO.getDataNascimento());
        socioBean.update(socioDTO.getNumeroSocio(),
                socioDTO.getNome(),
                socioDTO.getEmail(),
                socioDTO.getPassword(),
                dataNascimento.get(Calendar.DAY_OF_MONTH),
                dataNascimento.get(Calendar.MONTH),
                dataNascimento.get(Calendar.YEAR),
                socioDTO.getNumIdentificacaoCivil(),
                socioDTO.getNumContribuinte(),
                socioDTO.getMorada());
        Socio socio = socioBean.findSocio(email);
        return Response.status(Response.Status.OK)
                .entity(socioToDTO(socio))
                .build();
    }

    @DELETE
    @Path("{email}")
    @RolesAllowed({"Administrador"})
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
        securityContext.isUserInRole("Socio") && principal.getName().equals(email)) {
            Socio socio = socioBean.findSocio(email);
            return Response.status(Response.Status.OK)
                    .entity(socioToDTO(socio))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }


    // Converts an entity Atleta to a DTO Atleta class
    SocioDTO socioToDTO(Socio socio){
        SocioDTO socioDTO = new SocioDTO(
                socio.getNumeroSocio(),
                socio.getNome(),
                socio.getEmail(),
                socio.getPassword(),
                socio.getDataNascimento(),
                socio.getNumIdentificacaoCivil(),
                socio.getNumContribuinte(),
                socio.getMorada()
        );

        return socioDTO;
    }

    SocioDTO toDTONoModalidades (Socio socio){
        return new SocioDTO(
                socio.getNumeroSocio(),
                socio.getNome(),
                socio.getEmail(),
                socio.getPassword(),
                socio.getDataNascimento(),
                socio.getNumIdentificacaoCivil(),
                socio.getNumContribuinte(),
                socio.getMorada()
        );
    }

    List<SocioDTO> toDTOsNoModalidades(List<Socio> socios) {
        return socios.stream().map(this::toDTONoModalidades).collect(Collectors.toList());

    }

}
