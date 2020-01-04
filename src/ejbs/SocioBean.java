package ejbs;

import entities.Modalidade;
import entities.Socio;
import entities.Treinador;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Stateless(name = "SocioEJB")
public class SocioBean {

    @EJB
    private ModalidadeBean modalidadeBean;

    @PersistenceContext
    private EntityManager em;

    public SocioBean() {
    }

    public Socio create(long numeroSocio, String nome,String password,String email,int dia, int mes, int ano,long numIdentificacaoCivil, long numContribuinte, String morada) throws MyConstraintViolationException, MyEntityAlreadyExistsException,MyIllegalArgumentException {
        try{
            Socio socio = em.find(Socio.class,email);
            if(socio != null){
                throw new MyEntityAlreadyExistsException("Socio with email: " + email + " already exists");
            }
            socio = new Socio(numeroSocio,nome,password,email,new GregorianCalendar(ano,mes,dia),numIdentificacaoCivil,numContribuinte,morada);
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

    public Socio update(long numeroSocio, String nome, String email, String password, int dia,int mes, int ano,long numIdentificacaoCivil, long numContribuinte, String morada) throws MyConstraintViolationException, MyEntityAlreadyExistsException, MyIllegalArgumentException, MyEntityNotFoundException {
        try {
            Socio socio = em.find(Socio.class, email);
            if (socio == null) {
                throw new MyEntityNotFoundException("Socio não encontrado");
            }
            socio.setNumeroSocio(numeroSocio);
            socio.setPassword(password);
            socio.setNome(nome);
            socio.setEmail(email);
            socio.setDataNascimento(new GregorianCalendar(dia,mes,ano));
            socio.setMorada(morada);
            socio.setNumContribuinte(numContribuinte);
            socio.setNumIdentificacaoCivil(numIdentificacaoCivil);
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

    public List <Modalidade> getModalidadesNaoSubscritas (String email) throws MyEntityNotFoundException {
        try{
            Socio socio = (Socio) em.find(Socio.class, email);
            if(socio == null){
                throw new MyEntityNotFoundException("Socio with email " + email + " not found.");
            }
            List <Modalidade> modalidades = modalidadeBean.all();
            Set<Modalidade> modalidadesSubscritas = socio.getModalidades();
            List<Modalidade> modalidadesNaoSubscritas = new LinkedList<>();
            for(Modalidade modalidade: modalidades){
                if(!modalidadesSubscritas.contains(modalidade)){
                    modalidadesNaoSubscritas.add(modalidade);
                }
            }
            return modalidadesNaoSubscritas;
        }catch (MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_SOCIO_UNSUBSCRIBING_MODALIDADE ---->" + e.getMessage());
        }
    }
}
