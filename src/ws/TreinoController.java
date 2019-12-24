package ws;

import dtos.TreinoDTO;
import ejbs.TreinoBean;
import entities.Treino;

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
                    treinoDTO.getIdGraduacao(),
                    treinoDTO.getIdEscalao(),
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
    public Response updateTreino(@PathParam("code") int code, TreinoDTO treinoDTO){
        Principal principal = securityContext.getUserPrincipal();
        System.out.println("Treino: " + treinoDTO.getCode() + " --- " + principal.getName());
        if(securityContext.isUserInRole("Administrador")) {
            treinoBean.update(
                    code,
                    treinoDTO.getEmailTreinador(),
                    treinoDTO.getSiglaModalidade(),
                    treinoDTO.getIdGraduacao(),
                    treinoDTO.getIdEscalao(),
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

    TreinoDTO toDTO (Treino treino){
        return new TreinoDTO(
                treino.getCode(),
                treino.getTreinador().getNome(),
                treino.getModalidade().getSigla(),
                treino.getGraduacao().getId(),
                treino.getEscalao().getId(),
                treino.getHoraInicio(),
                treino.getHoraFim(),
                treino.getDiaSemana()
        );
    }

    List <TreinoDTO> toDTOs (List <Treino> treinos){
        return treinos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
