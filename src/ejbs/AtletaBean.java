package ejbs;

import entities.Atleta;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless(name = "AtletaEJB")
public class AtletaBean {

    @PersistenceContext
    private EntityManager em;

    public Atleta create(long numeroSocio, String nome, String email, String password, Date dataNascimento){
        try{
            Atleta atleta = new Atleta(1,nome,email,password, dataNascimento);
            em.persist(atleta);
            return atleta;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public Atleta update (long numeroSocio,String nome, String email,String password, int idade) throws MyEntityNotFoundException {
        try {
            Atleta atleta = em.find(Atleta.class, email);

            if (atleta == null) {
                throw new MyEntityNotFoundException("Atleta não encontrado!");
            }

            //O que faz?
            em.lock(atleta, LockModeType.OPTIMISTIC);

            atleta.setNumeroSocio(numeroSocio);
            atleta.setPassword(password);
            atleta.setNome(nome);
            atleta.setEmail(email);

            em.merge(atleta);

            return atleta;
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR UPDATING USER", e);
        }
    }

    public List<Atleta> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Atleta>) em.createNamedQuery("getAllAtletas").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ATLETAS", e);
        }
    }

    public Atleta findAtleta(String email) {
        try{
            return em.find(Atleta.class, email);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ATLETA", e);
        }
    }

    public void delete(String email){
        try {
            em.remove(findAtleta(email));
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_ATLETA",e);
        }
    }
}
