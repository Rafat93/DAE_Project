package ws;

import dtos.GraduacaoDTO;
import ejbs.GraduacaoBean;
import entities.Graduacao;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("/graduacoes") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class GraduacaoController {

    @EJB
    private GraduacaoBean graduacaoBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public List<GraduacaoDTO> all(){
        return toDTOs(graduacaoBean.all());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrador"})
    public Response createNewGraduacao (GraduacaoDTO graduacaoDTO) throws MyEntityAlreadyExistsException, MyEntityNotFoundException {
        Graduacao graduacao = graduacaoBean.create(
                graduacaoDTO.getCode(),
                graduacaoDTO.getNome(),
                graduacaoDTO.getSiglaModalidade()
        );
        return Response.status(Response.Status.CREATED).
                entity(toDTO(graduacao)).
                build();
    }

    GraduacaoDTO toDTO(Graduacao graduacao) {
        return new GraduacaoDTO(
                graduacao.getCode(),
                graduacao.getNome(),
                graduacao.getModalidade().getSigla()
        );
    }

    List<GraduacaoDTO> toDTOs(List<Graduacao> graduacoes) {
        return graduacoes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
