package ws;

import dtos.EscalaoDTO;
import dtos.ModalidadeDTO;
import dtos.ProdutoDTO;
import ejbs.EscalaoBean;
import entities.Atleta;
import entities.Escalao;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("/escaloes") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class EscalaoController {

    @EJB
    private EscalaoBean escalaoBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public List<EscalaoDTO> all() {
        return toDTOs(escalaoBean.all());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrador"})
    public Response createNewEscalao (EscalaoDTO escalaoDTO) throws MyEntityAlreadyExistsException, MyEntityNotFoundException {
        Escalao escalao = escalaoBean.create(
                escalaoDTO.getCode(),
                escalaoDTO.getNome(),
                escalaoDTO.getIdadeMin(),
                escalaoDTO.getIdadeMax(),
                escalaoDTO.getSiglaModalidade()
        );
        return Response.status(Response.Status.CREATED).
                entity(toDTO(escalao)).
                build();
    }

    @PUT
    @Path("{code}/modalidade/enroll/{sigla}")
    @RolesAllowed({"Administrator"})
    public Response enrollEscalaoInModalidade(@PathParam("code") String code, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        escalaoBean.enrollEscalaoInModalidade(code, sigla);
        return  Response.status(Response.Status.OK).build();
    }

    List<EscalaoDTO> toDTOs(List<Escalao> escaloes) {
        return escaloes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    EscalaoDTO toDTO(Escalao escalao) {
        return new EscalaoDTO(
                escalao.getCode(),
                escalao.getNome(),
                escalao.getIdadeMin(),
                escalao.getIdadeMax(),
                escalao.getModalidade().getSigla()
        );
    }
}
