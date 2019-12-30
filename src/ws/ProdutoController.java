package ws;

import dtos.ModalidadeDTO;
import dtos.ProdutoDTO;
import ejbs.ProdutoBean;
import entities.Produto;
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

@Path("/produtos") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ProdutoController {

    @EJB
    private ProdutoBean produtoBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public List<ProdutoDTO> all() {
        return toDTOs(produtoBean.all());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrador"})
    public Response createNewProduto(ProdutoDTO produtoDTO) throws MyEntityAlreadyExistsException {
        Produto produto = produtoBean.create(
                produtoDTO.getCode(),
                produtoDTO.getTipo(),
                produtoDTO.getDescricao(),
                produtoDTO.getPreco(),
                produtoDTO.getStock()
        );
        return Response.status(Response.Status.CREATED).
                entity(toDTO(produto)).
                build();
    }

    @GET
    @Path("/{code}")
    public Response getProdutoDetails (@PathParam("code") String code) {
        Produto produto = produtoBean.findProduto(code);
        return Response.status(Response.Status.OK)
                .entity(toDTO(produto))
                .build();
    }

    @DELETE
    @Path("{code}")
    @RolesAllowed({"Administrador"})
    public Response removeProduto (@PathParam("code") String code) {
        produtoBean.delete(code);
        return Response.status(Response.Status.OK).build();
    }



    ProdutoDTO toDTO(Produto produto){
        return new ProdutoDTO(
                produto.getCode(),
                produto.getTipo(),
                produto.getDescricao(),
                produto.getPrecoEmEuros(),
                produto.getStock()
        );
    }

    List <ProdutoDTO> toDTOs(List<Produto> produtos){
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
