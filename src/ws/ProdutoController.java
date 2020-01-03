package ws;

import dtos.ModalidadeDTO;
import dtos.ProdutoDTO;
import ejbs.ProdutoBean;
import ejbs.TipoProdutoBean;
import entities.Produto;
import entities.TipoProduto;
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

    @EJB
    private TipoProdutoBean tipoProdutoBean;

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
        TipoProduto tipoProduto = tipoProdutoBean.findTipoProduto(produtoDTO.getTipo());
        if(tipoProduto != null) {
            Produto produto = produtoBean.create(
                    produtoDTO.getCode(),
                    tipoProduto,
                    produtoDTO.getDescricao(),
                    produtoDTO.getPreco(),
                    produtoDTO.getStock()
            );
            return Response.status(Response.Status.CREATED).
                    entity(toDTO(produto)).
                    build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Tipo Produto com o nome  "+produtoDTO.getTipo()+ " não existe")
                .build();
    }

    @PUT
    @Path("{code}")
    @RolesAllowed({"Administrador"})
    public Response update(@PathParam("code") String code, ProdutoDTO produtoDTO) throws MyEntityNotFoundException {
        TipoProduto tipoProduto = tipoProdutoBean.findTipoProduto(produtoDTO.getTipo());
        if(tipoProduto != null) {
            produtoBean.update(
                    produtoDTO.getCode(),
                    tipoProduto,
                    produtoDTO.getDescricao(),
                    produtoDTO.getPreco(),
                    produtoDTO.getStock()
            );
            Produto produto = produtoBean.findProduto(code);
            return Response.status(Response.Status.OK).
                    entity(toDTO(produto)).
                    build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Tipo Produto com o nome  "+produtoDTO.getTipo()+ " não existe")
                .build();
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
                produto.getTipo().getNome(),
                produto.getDescricao(),
                produto.getPrecoEmEuros(),
                produto.getStock()
        );
    }

    List <ProdutoDTO> toDTOs(List<Produto> produtos){
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
