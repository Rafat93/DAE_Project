package ejbs;

import entities.Atleta;
import entities.Clube;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "ClubeEJB")
public class ClubeBean {

    @PersistenceContext
    private EntityManager em;

    public Clube create(long nif,String sigla, String nome, String email, String descricao, String morada, long telefone) throws MyEntityAlreadyExistsException {
        try{
            Clube clube = em.find(Clube.class,nif);
            if(clube != null){
                throw new MyEntityAlreadyExistsException("Clube com o nif: " + nif + " já existe");
            }
            clube = new Clube(nif,sigla,nome,email,descricao,morada,telefone);
            em.persist(clube);
            return  clube;
        }catch(MyEntityAlreadyExistsException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR CREATING CLUBE", e);
        }
    }

    public Clube clube(){
        try {
            return (Clube) em.createNamedQuery("getClube").getSingleResult();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_CLUBE", e);
        }
    }

    public Clube findClube(long nif){
        try{
            return em.find(Clube.class,nif);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_CLUBE", e);
        }
    }

    public Clube update (long nif,String sigla, String nome, String email, String descricao, String morada, long telefone) throws MyEntityNotFoundException {
        try{
            Clube clube = em.find(Clube.class,nif);
            if(clube == null){
                throw new MyEntityNotFoundException("Clube não encontrado!");
            }

            clube.setDescricao(descricao);
            clube.setSigla(sigla);
            clube.setEmail(email);
            clube.setMorada(morada);
            clube.setNome(nome);
            clube.setNif(nif);
            clube.setTelefone(telefone);

            return clube;
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR UPDATING CLUBE", e);
        }
    }
}
