package ejbs;

import entities.Administrador;
import exceptions.MyEntityAlreadyExistsException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless(name = "AdministradorEJB")
public class AdministradorBean {

    private EntityManager em;

    public Administrador create (String nome, String email, String password)throws MyEntityAlreadyExistsException{
        try {
            if(em.find(Administrador.class, email) != null){
                throw new MyEntityAlreadyExistsException("Admin with email: " + email + " already exists");
            }
            Administrador administrador = new Administrador(nome,email,password);
            em.persist(administrador);
            return administrador;
        } catch (MyEntityAlreadyExistsException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
