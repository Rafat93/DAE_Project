package ejbs;

import entities.Modalidade;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

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

    public Modalidade create (String sigla, String nome, String epocaDesportiva) throws MyEntityAlreadyExistsException {
        try {
            Modalidade modalidade = em.find(Modalidade.class,sigla);
            if (modalidade != null){
                throw new MyEntityAlreadyExistsException("Modalidade com a sigla "+sigla+"já existe");
            }
            System.out.println("ModalidadeBean: Epoca Desportiva"+epocaDesportiva);
            modalidade = new Modalidade(sigla,nome,epocaDesportiva);
            em.persist(modalidade);
            return modalidade;
        } catch (MyEntityAlreadyExistsException e) {
            throw e;
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }

    public Modalidade update (String sigla, String nome, String epocaDesportiva) throws MyEntityNotFoundException {
        try {
            Modalidade modalidade = em.find(Modalidade.class, sigla);
            if(modalidade ==null){
                throw new MyEntityNotFoundException("Modalidade não encontrada!");
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
            return (List<Modalidade>) em.createNamedQuery("getAllModalidades").getResultList();
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

    public void delete(String sigla) throws MyEntityNotFoundException {
        try {
            Modalidade modalidade = em.find(Modalidade.class,sigla);
            if(modalidade == null){
                throw new MyEntityNotFoundException("Modalidade com a sigla: " + sigla + " não existe");
            }
            em.remove(findModalidade(sigla));
        }catch (MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_MODALIDADE",e);
        }
    }

    //TODO: funções para remover elementos das listas
}
