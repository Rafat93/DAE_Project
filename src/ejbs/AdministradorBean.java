package ejbs;

import entities.Administrador;
import exceptions.MyEntityAlreadyExistsException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "AdministradorEJB")
public class AdministradorBean {

    @PersistenceContext
    private EntityManager em;

    public void create (String nome,String password, String email) throws MyEntityAlreadyExistsException{
        try {
            Administrador administrador = em.find(Administrador.class, email);
            if( administrador != null){
                throw new MyEntityAlreadyExistsException("Admin with email: " + email + " already exists");
            }
            em.persist(new Administrador(nome,password,email));
            em.flush();
        } catch (MyEntityAlreadyExistsException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException("ERRO no AdministratorBean" + e.getMessage());
        }
    }

}
