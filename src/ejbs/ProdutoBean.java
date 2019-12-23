package ejbs;

import entities.Produto;
import entities.TipoProduto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "ProdutoEJB")
public class ProdutoBean {

    @PersistenceContext
    EntityManager em;

    public ProdutoBean() {
    }

    public Produto create(TipoProduto tipo, String descricao, double preco, int stock){
        try{
            Produto produto = new Produto(tipo,descricao,preco,stock);
            em.persist(produto);
            return produto;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }
}
