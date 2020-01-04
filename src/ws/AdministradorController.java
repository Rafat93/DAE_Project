package ws;

import dtos.AdministradorDTO;
import ejbs.AdministradorBean;
import entities.Administrador;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("/administradores") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AdministradorController {

    @EJB
    private AdministradorBean administradorBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    @RolesAllowed({"Administrador"})
    public List <AdministradorDTO> all(){
        return toDTOs(administradorBean.all());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrador"})
    public Response createNewAdministrador (AdministradorDTO administradorDTO) throws MyEntityAlreadyExistsException {
       // System.out.println("ADMINCONTROLLER: "+administradorDTO.getEmail()+"/"+administradorDTO.getNome()+"/"+administradorDTO.getPassword());
        Administrador administrador = administradorBean.create(
                administradorDTO.getNome(),
                administradorDTO.getPassword(),
                administradorDTO.getEmail());
        return  Response.status(Response.Status.CREATED).entity(toDTO(administrador)).build();
    }

    @PUT
    @Path("{email}")
    @RolesAllowed({"Administrador"})
    public Response update(@PathParam("email") String email,AdministradorDTO administradorDTO)  throws MyEntityNotFoundException {
        administradorBean.update(
                administradorDTO.getNome(),
                administradorDTO.getEmail());
        Administrador administrador = administradorBean.findAdministrador(email);
        return Response.status(Response.Status.OK).entity(toDTO(administrador)).build();
    }

    @DELETE
    @Path("{email}")
    @RolesAllowed({"Administrador"})
    public Response removeAdministrador (@PathParam("email") String email)
            throws MyEntityNotFoundException {
        administradorBean.delete(email);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{email}")
    @RolesAllowed({"Administrador"})
    public Response getAministradorDetails (@PathParam("email") String email) throws MyEntityAlreadyExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Administrador administrador = administradorBean.findAdministrador(email);
        return Response.status(Response.Status.OK)
                .entity(toDTO(administrador))
                .build();
    }

        AdministradorDTO toDTO (Administrador administrador){
        return  new AdministradorDTO(administrador.getNome(),administrador.getEmail(),administrador.getPassword());
    }

    List <AdministradorDTO> toDTOs(List <Administrador> administradores){
        return administradores.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
