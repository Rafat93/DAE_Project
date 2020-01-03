package ws;

import dtos.GraduacaoDTO;
import ejbs.GraduacaoBean;
import entities.Escalao;
import entities.Graduacao;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

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
    @RolesAllowed({"Administrador"})
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
        System.out.println("Criou Graduação");
        return Response.status(Response.Status.CREATED).
                entity(toDTO(graduacao)).
                build();
    }

    @PUT
    @Path("{code}/modalidade/enroll/{sigla}")
    @RolesAllowed({"Administrator"})
    public Response enrollGraduacaoInModalidade(@PathParam("code") String code, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        graduacaoBean.enrollGraduacaoInModalidade(code, sigla);
        return  Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("{code}/modalidade/unroll/{sigla}")
    @RolesAllowed({"Administrator"})
    public Response unrollGraduacaoFromModalidade(@PathParam("code") String code, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        graduacaoBean.unrollGraduacaoFromModalidade(code, sigla);
        return  Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{code}")
    @RolesAllowed({"Administrador"})
    public Response removeGraduacao (@PathParam("code") String code){
        graduacaoBean.delete(code);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{code}")
    public Response getEscalaoDetails (@PathParam("code") String code) {
        Graduacao graduacao = graduacaoBean.findGraduacao(code);
        return  Response.status(Response.Status.OK).entity(toDTO(graduacao)).build();
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
