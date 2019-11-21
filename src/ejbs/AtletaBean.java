package ejbs;

import entities.Atleta;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "AtletaEJB")
public class AtletaBean {

    @PersistenceContext
    private EntityManager em;

    public AtletaBean() {
    }

    public Atleta create(int id,String nome, String email, int idade){
        try{
            Atleta atleta = new Atleta(id,nome,email,idade);
            em.persist(atleta);
            return atleta;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }
}
