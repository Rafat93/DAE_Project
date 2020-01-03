package ws;

import dtos.TipoProdutoDTO;
import ejbs.TipoProdutoBean;
import entities.TipoProduto;
import exceptions.MyEntityAlreadyExistsException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tipo_produtos") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TipoProdutoController {

    @EJB
    private TipoProdutoBean tipoProdutoBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    @RolesAllowed({"Administrador"})
    public List<TipoProdutoDTO> all(){
        return toDTOs(tipoProdutoBean.all());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrador"})
    public Response createNewTipoProduto (TipoProdutoDTO tipoProdutoDTO) throws MyEntityAlreadyExistsException {
        TipoProduto tipoProduto = tipoProdutoBean.create(tipoProdutoDTO.getNome());
        return Response.status(Response.Status.OK).entity(toDTO(tipoProduto)).build();
    }

    TipoProdutoDTO toDTO(TipoProduto tipoProduto){
        return new TipoProdutoDTO(tipoProduto.getNome());
    }

    List <TipoProdutoDTO> toDTOs (List <TipoProduto> tipoProdutos){
        return tipoProdutos.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
