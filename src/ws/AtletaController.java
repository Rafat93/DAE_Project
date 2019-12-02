package ws;

import dtos.AtletaDTO;
import ejbs.AtletaBean;
import entities.Atleta;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/atletas") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class AtletaController {

    @EJB
    private AtletaBean atletaBean;

    public static AtletaDTO toDTO(Atleta atleta) {
        return new AtletaDTO(
                atleta.getNumeroSocio(),
                atleta.getNome(),
                atleta.getEmail(),
                atleta.getIdade()
        );
    }

    public static List<AtletaDTO> toDTOs(List<Atleta> atletas) {
        return atletas.stream().map(AtletaController::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public List<AtletaDTO> all() {
        try {
            return toDTOs(atletaBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_STUDENTS", e);
        }
    }

}
