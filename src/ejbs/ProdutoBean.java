package ejbs;

import entities.Atleta;
import entities.Produto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "ProdutoEJB")
public class ProdutoBean {

    @PersistenceContext
    EntityManager em;

    public ProdutoBean() {
    }

    public Produto create(String tipo, String descricao, double preco){
        try{
            Produto produto = new Produto(tipo,descricao,preco);
            em.persist(produto);
            return produto;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }
}
