package ejbs;

import entities.Administrador;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "AdministradorEJB")
public class AdministradorBean {

    @PersistenceContext
    private EntityManager em;

    public Administrador create (String nome,String password, String email) throws MyEntityAlreadyExistsException{
        try {
            Administrador administrador = em.find(Administrador.class, email);
            if( administrador != null){
                throw new MyEntityAlreadyExistsException("Admin with email: " + email + " already exists");
            }
            administrador = new Administrador(nome,password,email);
            em.persist(administrador);
            return administrador;
        } catch (MyEntityAlreadyExistsException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException("ERRO no AdministratorBean" + e.getMessage());
        }
    }

    public Administrador update (String nome, String email) throws MyEntityNotFoundException {
        try {
            Administrador administrador = em.find(Administrador.class, email);
            if (administrador == null) {
                throw new MyEntityNotFoundException("Administrador não encontrado!");
            }
            em.lock(administrador, LockModeType.OPTIMISTIC);
            administrador.setEmail(email);
            administrador.setNome(nome);

            em.merge(administrador);
            return administrador;
        } catch (MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR UPDATING ADMINISTRADOR", e);
        }
    }

    public List<Administrador> all(){
        try{
            return (List <Administrador>) em.createNamedQuery("getAllAdmins").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ADMINS",e);
        }
    }

    public Administrador findAdministrador (String email){
        try{
            return em.find(Administrador.class,email);
        }catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ADMINS", e);
        }
    }

    public void delete (String email) throws MyEntityNotFoundException {
        try {
            Administrador administrador = em.find(Administrador.class, email);
            if(administrador == null) {
                throw new MyEntityNotFoundException("Administrador com o email: " + email + " não existe");
            }
            em.remove(findAdministrador(email));
        }catch (MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_ATLETA",e);
        }
    }
}
