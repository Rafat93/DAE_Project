package ejbs;

import entities.Atleta;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "AtletaEJB")
public class AtletaBean {

    @PersistenceContext
    private EntityManager em;

    public Atleta create(int id,String nome, String email, int idade){
        try{
            Atleta atleta = new Atleta(id,nome,email,idade);
            em.persist(atleta);
            return atleta;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public List<Atleta> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Atleta>) em.createNamedQuery("getAllAtletas").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_STUDENTS", e);
        }
    }
}
