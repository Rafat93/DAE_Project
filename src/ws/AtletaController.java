package ws;

import dtos.AtletaDTO;
import ejbs.AtletaBean;
import entities.Atleta;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("/atletas") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class AtletaController {

    @EJB
    private AtletaBean atletaBean;

    @Context
    private SecurityContext securityContext;

    public static AtletaDTO toDTO(Atleta atleta, Function<AtletaDTO,AtletaDTO> fn) {
        AtletaDTO dto = new AtletaDTO(
                atleta.getNumeroSocio(),
                atleta.getNome(),
                atleta.getEmail(),
                atleta.getPassword(),
                atleta.getIdade()
        );
        if(fn != null){
            return fn.apply(dto);
        }
        return dto;
    }

    public static Set<AtletaDTO> toDTOs(Collection<Atleta> atletas,Function<AtletaDTO,AtletaDTO> fn) {
        return atletas.stream().map(s -> AtletaController.toDTO(s, fn)).collect(Collectors.toSet());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public Response all() {
        try {
            return Response.status(Response.Status.OK).entity(toDTOs(atletaBean.all(), null)).build();

        } catch (Exception e) {
            throw new EJBException("ERROR_GET_STUDENTS", e);
        }
    }

}
