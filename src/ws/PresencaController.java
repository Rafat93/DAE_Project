package ws;

import dtos.AtletaDTO;
import dtos.PresencaDTO;
import dtos.SocioDTO;
import ejbs.PresencaBean;
import entities.Atleta;
import entities.Presenca;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


@Path("/presenca") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PresencaController {

    @EJB
    private PresencaBean presencaBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public List<PresencaDTO> all() {
        return toDTOs(presencaBean.all());
    }

    @GET
    @Path("treino/{code}")
    public List<PresencaDTO> getPresencasTreino(@PathParam("code") String code) throws MyEntityNotFoundException {
        return toDTOs(presencaBean.findPresencasTreino(code));
    }

    @POST
    @Path("/")
    public Response createNewPresenca(PresencaDTO presencaDTO){
        Presenca presenca = presencaBean.create(
                presencaDTO.getCode(),
                presencaDTO.getCodeTreino(),
                presencaDTO.getDataTreino().get(Calendar.DAY_OF_MONTH),
                presencaDTO.getDataTreino().get(Calendar.MONTH),
                presencaDTO.getDataTreino().get(Calendar.YEAR),
                presencaDTO.getAtletasPresentes(),
                presencaDTO.getEmailTreinador()
        );
        return Response.status(Response.Status.CREATED).entity(toDTO(presenca)).build();
    }

    @PUT
    @Path("{code}")
    public Response updatePresenca(@PathParam("code") String code,PresencaDTO presencaDTO) throws MyEntityNotFoundException {
        presencaBean.update(
                presencaDTO.getCode(),
                presencaDTO.getCodeTreino(),
                presencaDTO.getDataTreino().get(Calendar.DAY_OF_MONTH),
                presencaDTO.getDataTreino().get(Calendar.MONTH),
                presencaDTO.getDataTreino().get(Calendar.YEAR),
                presencaDTO.getAtletasPresentes(),
                presencaDTO.getEmailTreinador()
        );
        Presenca presenca = presencaBean.findPresenca(code);
        return Response.status(Response.Status.OK).entity(toDTO(presenca)).build();
    }

    @DELETE
    @Path("{code}")
    @RolesAllowed({"Administrator"})
    public Response removePresenca(@PathParam("code") String code) throws MyEntityNotFoundException {
        presencaBean.delete(code);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{code}")
    public Response getPresencaDetails(@PathParam("code") String code){
        Principal principal = securityContext.getUserPrincipal();
        System.out.println(code + " --- " + principal.getName());
        Presenca presenca = presencaBean.findPresenca(code);
        if(securityContext.isUserInRole("Administrador") ||
                securityContext.isUserInRole("Treinador") && principal.getName().equals(presenca.getTreinador().getNome())) {
            return Response.status(Response.Status.OK)
                    .entity(toDTO(presenca))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();

    }

    PresencaDTO toDTO(Presenca presenca) {
        PresencaDTO presencaDTO = new PresencaDTO(
                presenca.getCode(),
                presenca.getTreino().getCode(),
                presenca.getDataTreino(),
                presenca.getTreinador().getEmail()

        );
        List<String> atletaDTOS = atletaToDTOs(presenca.getAtletasPresentes());
        presencaDTO.setAtletasPresentes(atletaDTOS);
        return presencaDTO;
    }

    List<PresencaDTO> toDTOs(List<Presenca> presencas) {
        return presencas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    String atletaToDTO(Atleta atleta) {
        return atleta.getEmail();
    }

    List<String> atletaToDTOs(List<Atleta> atletas) {
        return atletas.stream().map(this::atletaToDTO).collect(Collectors.toList());
    }
}
