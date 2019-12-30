package ejbs;

import entities.Compra;
import entities.Escalao;
import entities.Modalidade;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "EscalaoEJB")
public class EscalaoBean {

    @PersistenceContext
    private EntityManager em;

    public List<Escalao> all(){
        try{
            return (List<Escalao>) em.createNamedQuery("getAllEscaloes").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ESCALOES", e);
        }
    }

    public Escalao create (String code, String nome, int idadeMin, int idadeMax, String sigla) throws MyEntityAlreadyExistsException, MyEntityNotFoundException {
        try{
            if(idadeMax < idadeMin){
                throw new NumberFormatException("Idade Maxima não podeser inferior à idade minima");
            }
            Escalao escalao = em.find(Escalao.class, code);
            if(escalao != null){
                throw new MyEntityAlreadyExistsException("Escalão com o codigo "+code+" já existe");
            }
            Modalidade modalidade = em.find(Modalidade.class, sigla);
            if(modalidade == null){
                throw new MyEntityNotFoundException("Modalidade com a sigla" + sigla + " não existe.");
            }
            escalao = new Escalao(code,nome,idadeMin,idadeMax,modalidade);
            em.persist(escalao);
            return escalao;
        }catch (MyEntityAlreadyExistsException | MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
