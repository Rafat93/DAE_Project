package ws;

import dtos.ModalidadeDTO;
import dtos.ProdutoDTO;
import ejbs.ProdutoBean;
import entities.Produto;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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
