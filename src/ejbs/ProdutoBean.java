package ejbs;

import entities.Compra;
import entities.Produto;
import entities.TipoProduto;
import exceptions.MyEntityAlreadyExistsException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "ProdutoEJB")
public class ProdutoBean {

    @PersistenceContext
    EntityManager em;

    public Produto create(String code, TipoProduto tipo, String descricao, double preco, int stock) throws MyEntityAlreadyExistsException {
        try {
            if (em.find(Produto.class, code) != null) {
                throw new MyEntityAlreadyExistsException("Produto com o codigo: " + code + " já existe");
            }

            Produto produto = new Produto(code, tipo, descricao, preco, stock);
            em.persist(produto);
            return produto;
        }catch (MyEntityAlreadyExistsException e) {
            throw e;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public List<Produto> all(){
        try{
            return (List<Produto>) em.createNamedQuery("getAllProdutos").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_Produtos", e);
        }
    }
}
