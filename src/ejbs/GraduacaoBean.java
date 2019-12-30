package ejbs;

import entities.Graduacao;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "GraduacaoEJB")
public class GraduacaoBean {

    @PersistenceContext
    private EntityManager em;

    public List<Graduacao> all(){
        try{
            return (List<Graduacao>) em.createNamedQuery("getAllGraduacoes").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_GRADUACOES", e);
        }
    }

    /*public Graduacao create (String code, String nome, String sigla) throws MyEntityAlreadyExistsException, MyEntityNotFoundException {
        try{

        }catch (MyEntityAlreadyExistsException | MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }*/
}
