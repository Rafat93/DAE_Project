package ejbs;

import entities.Atleta;
import entities.Modalidade;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.GregorianCalendar;
import java.util.List;

@Stateless(name = "AtletaEJB")
public class AtletaBean {

    @PersistenceContext
    private EntityManager em;

    public Atleta create(long numeroSocio, String nome, String email, String password, int dia, int mes, int ano,long numIdentificacaoCivil, long numContibuinte, String morada){
        try{
            Atleta atleta = em.find(Atleta.class,email);
            if(atleta != null){
                throw new MyEntityAlreadyExistsException("Atleta com o email: " + email + " já existe");
            }
            atleta = new Atleta(numeroSocio,nome,email,password, new GregorianCalendar(ano,mes,dia),numIdentificacaoCivil,numContibuinte,morada);
            em.persist(atleta);
            em.flush();
            return atleta;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public Atleta update (long numeroSocio,String nome, String email,String password, int dia,int mes, int ano,long numIdentificacaoCivil, long numContibuinte, String morada) throws MyEntityNotFoundException {
        try {
            Atleta atleta = em.find(Atleta.class, email);

            if (atleta == null) {
                throw new MyEntityNotFoundException("Atleta não encontrado!");
            }
            em.lock(atleta, LockModeType.OPTIMISTIC);

            atleta.setNumeroSocio(numeroSocio);
            atleta.setPassword(password);
            atleta.setNome(nome);
            atleta.setEmail(email);
            atleta.setDataNascimento(new GregorianCalendar(dia,mes,ano));
            atleta.setMorada(morada);
            atleta.setNumContibuinte(numContibuinte);
            atleta.setNumIdentificacaoCivil(numIdentificacaoCivil);

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

    public void delete(String email) throws MyEntityNotFoundException{
        try {
            Atleta atleta = em.find(Atleta.class,email);
            if (atleta == null) {
                throw new MyEntityNotFoundException("Atleta com o email: " + email + " não existe");
            }
            em.remove(findAtleta(email));
        }catch (MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_ATLETA",e);
        }
    }

    public void enrollAtletaInModalidade(String email, String sigla) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try {
            Atleta atleta = (Atleta) em.find(Atleta.class,email);
            if(atleta ==null){
                throw new MyEntityNotFoundException("Atleta with email " + email + " not found.");
            }
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,sigla);
            if (modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade with code " + sigla + " not found.");
            }
            if(modalidade.getAtletas().contains(atleta)){
                throw new MyIllegalArgumentException("Atleta is already enrolled in modalidade with code " + sigla);
            }
            modalidade.addAtleta(atleta);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_ENROLLING_ATLETA_IN_MODALIDADE ---->" + e.getMessage());
        }
    }

    public void unrollAtletaFromModalidade(String email, String sigla) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Atleta atleta = (Atleta) em.find(Atleta.class,email);
            if(atleta ==null){
                throw new MyEntityNotFoundException("Atleta com email " + email + " não existe.");
            }
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,sigla);
            if (modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade com a sigla " + sigla + " não existe.");
            }
            if(!modalidade.getAtletas().contains(atleta)){
                throw new MyIllegalArgumentException("Atleta não existe na Modalidade com a sigla " + sigla);
            }
            modalidade.removeAtleta(atleta);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_UNROLLING_ATLETA_FROM_MODALIDADE ---->" + e.getMessage());
        }
    }
}
