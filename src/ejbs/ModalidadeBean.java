package ejbs;

import entities.Modalidade;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "ModalidadeEJB")
public class ModalidadeBean {

    @PersistenceContext
    private EntityManager em;

    public Modalidade create (String sigla, String nome, String epocaDesportiva){
        try {
            Modalidade modalidade = new Modalidade(sigla,nome,epocaDesportiva);
            em.persist(modalidade);
            return modalidade;
        }catch (Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public Modalidade update (String sigla, String nome, String epocaDesportiva) throws MyEntityNotFoundException {
        try {
            Modalidade modalidade = em.find(Modalidade.class, sigla);
            if(modalidade ==null){
                throw new MyEntityNotFoundException("Modalidade não encontrado!");
            }

            em.lock(modalidade, LockModeType.OPTIMISTIC);

            modalidade.setSigla(sigla);
            modalidade.setNome(nome);
            modalidade.setEpocaDesportiva(epocaDesportiva);

            em.merge(modalidade);

            return modalidade;
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR UPDATING MODALIDADE", e);
        }
    }

    public List<Modalidade> all(){
        try {
            return (List<Modalidade>) em.createNamedQuery("getAllSocios").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_MODALIDADES", e);
        }
    }

    public Modalidade findModalidade(String sigla){
        try {
            return em.find(Modalidade.class,sigla);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_MODALIDADE", e);
        }
    }

    public void delete(String sigla){
        try {
            em.remove(findModalidade(sigla));
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_MODALIDADE",e);
        }
    }

}
