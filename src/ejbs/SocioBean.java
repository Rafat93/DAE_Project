package ejbs;

import entities.Socio;
import entities.Socio;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "SocioEJB")
public class SocioBean {

    @PersistenceContext
    private EntityManager em;

    public SocioBean() {
    }

    public Socio create(int numeroSocio, String nome, String email, String password){
        try{
            Socio socio = new Socio(1,nome,email,password);
            em.persist(socio);
            return socio;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public List<Socio> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Socio>) em.createNamedQuery("getAllSocios").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_STUDENTS", e);
        }
    }
}
