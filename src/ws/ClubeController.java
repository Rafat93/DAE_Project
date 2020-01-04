package ws;

import dtos.ClubeDTO;
import ejbs.ClubeBean;
import entities.Clube;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clube") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ClubeController {

    @EJB
    private ClubeBean clubeBean;

    @GET
    @Path("/")
    @RolesAllowed({"Administrador"})
    public ClubeDTO clube(long nif){
        return toDTO(clubeBean.clube());
    }

    @PUT
    @Path("{nif}")
    public Response update(@PathParam("nif") long nif,ClubeDTO clubeDTO) throws MyEntityNotFoundException {
        clubeBean.update(
                nif,
                clubeDTO.getNome(),
                clubeDTO.getEmail(),
                clubeDTO.getDescricao(),
                clubeDTO.getMorada(),
                clubeDTO.getTelefone());
        Clube clube = clubeBean.findClube(nif);
        return Response.status(Response.Status.OK)
                .entity(toDTO(clube))
                .build();
    };

    ClubeDTO toDTO(Clube clube){
        return new ClubeDTO(
                clube.getNif(),
                clube.getSigla(),
                clube.getNome(),
                clube.getEmail(),
                clube.getDescricao(),
                clube.getMorada(),
                clube.getTelefone()
        );
    }
}
