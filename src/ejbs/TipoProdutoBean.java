package ejbs;

import entities.Inscricao;
import entities.Produto;
import entities.TipoProduto;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "TipoProdutoEJB")
public class TipoProdutoBean {

    @PersistenceContext
    EntityManager em;

    public TipoProduto create(String nome) throws MyEntityAlreadyExistsException {
        try {
            if (em.find(TipoProduto.class, nome) != null) {
                throw new MyEntityAlreadyExistsException("Tipode produto com o nome: " + nome + " já existe");
            }
            TipoProduto tipoProduto = new TipoProduto(nome);
            em.persist(tipoProduto);
            return tipoProduto;
        }catch (MyEntityAlreadyExistsException e) {
            throw e;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public TipoProduto findTipoProduto (String nome){
        try{
            return em.find(TipoProduto.class,nome);
        }catch (Exception e){
            throw new EJBException("ERROR_FINDING_TIPO_PRODUTO", e);
        }
    }

    public List<TipoProduto> all() {
        try {
            return (List<TipoProduto>) em.createNamedQuery("getAllTipoProdutos").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_TIPO_PRODUTOS", e);
        }
    }

    public void delete(String nome) throws MyEntityNotFoundException {
        try{
            TipoProduto tipoProduto = em.find(TipoProduto.class,nome);
            if (tipoProduto == null) {
                throw new MyEntityNotFoundException("TipoProduto com o nome: " + nome + " não existe");
            }
            em.remove(tipoProduto);
        }catch (MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_TREINADOR",e);
        }
    }
}
