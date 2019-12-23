package ejbs;

import entities.Socio;
import entities.Socio;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "SocioEJB")
public class SocioBean {

    @PersistenceContext
    private EntityManager em;

    public SocioBean() {
    }

    public Socio create(long numeroSocio, String nome, String email, String password) throws MyConstraintViolationException, MyEntityAlreadyExistsException,MyIllegalArgumentException {
        try{
            Socio socio = new Socio(numeroSocio,nome,email,password);
            em.persist(socio);
            return socio;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public List<Socio> all() {
        try {
            return (List<Socio>) em.createNamedQuery("getAllSocios").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_SOCIOS", e);
        }
    }

    public Socio findStudent(String email) {
        try{
            return em.find(Socio.class, email);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_SOCIO", e);
        }
    }

    public Socio update(long numeroSocio, String nome, String email, String password) throws MyConstraintViolationException, MyEntityAlreadyExistsException, MyIllegalArgumentException, MyEntityNotFoundException {
        try {
            Socio socio = em.find(Socio.class, email);

            if (socio == null) {
                throw new MyEntityNotFoundException("Socio não encontrado");
            }

            //O que faz?
            em.lock(socio, LockModeType.OPTIMISTIC);

            socio.setNumeroSocio(numeroSocio);
            socio.setPassword(password);
            socio.setNome(nome);
            socio.setEmail(email);

            em.merge(socio);

            return socio;
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR UPDATING SOCIO", e);
        }
    }

    public void delete(String email){
        try {
            em.remove(findStudent(email));
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_SOCIO",e);
        }
    }
}
