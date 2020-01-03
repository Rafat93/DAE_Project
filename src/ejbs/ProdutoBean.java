package ejbs;

import entities.Compra;
import entities.Produto;
import entities.TipoProduto;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
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

    public Produto update(String code, TipoProduto tipo, String descricao, double preco, int stock) throws MyEntityNotFoundException {
        try{
            Produto produto = em.find(Produto.class,code);
            if(produto == null){
                throw new MyEntityNotFoundException("Produto com o codigo "+code+" não  existe");
            }
            produto.setDescricao(descricao);
            produto.setTipo(tipo);
            if(preco <= 0){
                throw new NumberFormatException("Preço tem de ser maior que 0");
            }
            produto.setPrecoEmEuros(preco);
            if(stock < 0){
                throw new NumberFormatException("Stock tem de ser maior que 0");
            }
            produto.setStock(stock);
            return produto;
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR UPDATING PRODUTO", e);
        }
    }

    public void delete (String code){
        try{
            em.remove(findProduto(code));
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_PRODUTO");
        }
    }

    public Produto findProduto(String code) {
        try{
            return em.find(Produto.class,code);
        }catch (Exception e){
            throw new EJBException("ERROR_FINDING_PRODUTO", e);
        }
    }

    public Produto decrementStock(String code, int quantidade) throws MyEntityNotFoundException {
        try{
            Produto produto = em.find(Produto.class,code);
            if(produto == null){
                throw new MyEntityNotFoundException("Produto com o codigo "+code+" não  existe");
            }
            em.lock(produto, LockModeType.OPTIMISTIC);
            if(produto.getStock()-quantidade <0){
                throw new NullPointerException("Não existe stock suficiente do Produto "+code);
            }
            produto.setStock(produto.getStock()-quantidade);
            em.merge(produto);
            return produto;
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR UPDATING PRODUTO", e);
        }
    }

    public Produto incrementStock(String code, int quantidade) throws MyEntityNotFoundException {
        try{
            Produto produto = em.find(Produto.class,code);
            if(produto == null){
                throw new MyEntityNotFoundException("Produto com o codigo "+code+" não  existe");
            }
            em.lock(produto, LockModeType.OPTIMISTIC);
            produto.setStock(produto.getStock()+quantidade);
            em.merge(produto);
            return produto;
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR UPDATING PRODUTO", e);
        }
    }
}
