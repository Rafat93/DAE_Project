package ejbs;

import entities.Modalidade;
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
import java.util.GregorianCalendar;
import java.util.List;

@Stateless(name = "SocioEJB")
public class SocioBean {

    @PersistenceContext
    private EntityManager em;

    public SocioBean() {
    }

    public Socio create(long numeroSocio, String nome,String password,String email,int dia, int mes, int ano) throws MyConstraintViolationException, MyEntityAlreadyExistsException,MyIllegalArgumentException {
        try{
            Socio socio = em.find(Socio.class,email);
            if(socio != null){
                throw new MyEntityAlreadyExistsException("Socio with email: " + email + " already exists");
            }
            socio = new Socio(numeroSocio,nome,password,email,new GregorianCalendar(ano,mes,dia));
            em.persist(socio);
            em.flush();
            return socio;
        } catch (MyEntityAlreadyExistsException e) {
            throw e;
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }

    public List<Socio> all() {
        try {
            return (List<Socio>) em.createNamedQuery("getAllSocios").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_SOCIOS", e);
        }
    }

    public Socio findSocio(String email) {
        try{
            return em.find(Socio.class, email);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_SOCIO", e);
        }
    }

    public Socio update(long numeroSocio, String nome, String email, String password, int dia,int mes, int ano) throws MyConstraintViolationException, MyEntityAlreadyExistsException, MyIllegalArgumentException, MyEntityNotFoundException {
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
            socio.setDataNascimento(new GregorianCalendar(dia,mes,ano));

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
            em.remove(findSocio(email));
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_SOCIO",e);
        }
    }

    public void subscribeModalidade(String email, String sigla) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Socio socio = (Socio) em.find(Socio.class, email);
            if(socio == null){
                throw new MyEntityNotFoundException("Socio with email " + email + " not found.");
            }
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,sigla);
            if (modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade with code " + sigla + " not found.");
            }
            if(modalidade.getSocios().contains(socio)){
                throw new MyIllegalArgumentException("Socio have already subscribe this modalidade with code " + sigla);
            }
            socio.addModalidade(modalidade);
            modalidade.addSocio(socio);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_SOCIO_SUBSCRIBING_MODALIDADE ---->" + e.getMessage());
        }
    }

    public void unsubscribeModalidade(String email, String sigla) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Socio socio = (Socio) em.find(Socio.class, email);
            if(socio == null){
                throw new MyEntityNotFoundException("Socio with email " + email + " not found.");
            }
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,sigla);
            if (modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade with code " + sigla + " not found.");
            }
            if(modalidade.getSocios().contains(socio)){
                throw new MyIllegalArgumentException("Socio have already subscribe this modalidade with code " + sigla);
            }
            socio.removeModalidade(modalidade);
            modalidade.removeSocio(socio);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_SOCIO_UNSUBSCRIBING_MODALIDADE ---->" + e.getMessage());
        }
    }
}
