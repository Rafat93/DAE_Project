package ejbs;

import entities.Compra;
import entities.Pagamento;
import entities.User;
import enums.EstadoPagamento;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.GregorianCalendar;

@Stateless(name = "PagamentoEJB")
public class PagamentoBean {

    @PersistenceContext
    private EntityManager em;

    public Pagamento create(String code, String userEmail, String compraCode, GregorianCalendar dataCompra,GregorianCalendar dataLimite,double valorEmEuros, EstadoPagamento estadoPagamento) throws MyEntityNotFoundException, MyEntityAlreadyExistsException {
        try {
            if(em.find(Pagamento.class,code)!=null){
                throw new MyEntityAlreadyExistsException("Pagamento com o código: " + code + " já existe");
            }
            User user = em.find(User.class,userEmail);
            if(user == null){
                throw new MyEntityNotFoundException("Utilizador com o email: " + userEmail + " não existe.");
            }
            Compra compra = em.find(Compra.class,compraCode);
            if(compra == null){
                throw new MyEntityNotFoundException("Compra com o codigo " + compraCode + " não existe!");
            }
            Pagamento pagamento = new Pagamento(code,user,compra,dataCompra,dataLimite,valorEmEuros,estadoPagamento);
            em.persist(pagamento);
            return pagamento;
        }catch(MyEntityAlreadyExistsException | MyEntityNotFoundException e){
            throw e;
        } catch(Exception e){
            throw new EJBException("ERROR UPDATING USER", e);
        }
    }

    //Fazer pagamento e gera recibo
}
