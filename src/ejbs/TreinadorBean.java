package ejbs;


import entities.Treinador;
import entities.Modalidade;
import entities.Treinador;
import entities.Treino;
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

@Stateless(name = "TreinadorEJB")
public class TreinadorBean {

    @PersistenceContext
    private EntityManager em;

    public Treinador create(String nome, String password, String email,long numeroCedula){
        try{
            Treinador treinador = em.find(Treinador.class,email);
            if(treinador != null){
                throw new MyEntityAlreadyExistsException("Treinador com o email: " + email + " já existe");
            }
            treinador = new Treinador(nome,password,email,numeroCedula);
            em.persist(treinador);
            em.flush();
            return treinador;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public List<Treinador> all() {
        try {
            return (List<Treinador>) em.createNamedQuery("getAllTreinadores").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_TREINADORES", e);
        }
    }

    public Treinador update (String nome, String password, String email,long numeroCedula) throws MyEntityNotFoundException {
        try{
            Treinador treinador = em.find(Treinador.class,email);
            if(treinador == null){
                throw new MyEntityNotFoundException("Treinador não encontrado!");
            }
            treinador.setNumeroCedula(numeroCedula);
            treinador.setNome(nome);
            treinador.setEmail(email);
            treinador.setPassword(password);

            return  treinador;
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR UPDATING USER", e);
        }
    }

    public Treinador findTreinador (String email){
        try{
            return em.find(Treinador.class, email);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_TREINADOR", e);
        }
    }

    public void delete(String email) throws MyEntityNotFoundException{
        try {
            Treinador treinador = em.find(Treinador.class,email);
            if (treinador == null) {
                throw new MyEntityNotFoundException("Treinador com o email: " + email + " já existe");
            }
            em.remove(findTreinador(email));
        }catch (MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_TREINADOR",e);
        }
    }

    public void enrollTreinadorInModalidade(String email, String sigla) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try {
            Treinador treinador = (Treinador) em.find(Treinador.class,email);
            if(treinador ==null){
                throw new MyEntityNotFoundException("Treinador with email " + email + " not found.");
            }
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,sigla);
            if (modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade with code " + sigla + " not found.");
            }
            if(modalidade.getTreinadores().contains(treinador)){
                throw new MyIllegalArgumentException("Treinador is already enrolled in modalidade with code " + sigla);
            }
            modalidade.addTreinador(treinador);
            treinador.addModalidade(modalidade);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_ENROLLING_TREINADOR_IN_MODALIDADE ---->" + e.getMessage());
        }
    }

    public void unrollTreinadorFromModalidade(String email, String sigla) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Treinador treinador = (Treinador) em.find(Treinador.class,email);
            if(treinador ==null){
                throw new MyEntityNotFoundException("Treinador com email " + email + " não existe.");
            }
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,sigla);
            if (modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade com a sigla " + sigla + " não existe.");
            }
            if(!modalidade.getTreinadores().contains(treinador)){
                throw new MyIllegalArgumentException("Treinador não existe na Modalidade com a sigla " + sigla);
            }
            modalidade.removeTreinador(treinador);
            treinador.removeModalidade(modalidade);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_UNROLLING_TREINADOR_FROM_MODALIDADE ---->" + e.getMessage());
        }
    }

    public void enrollTreinadorInTreino(String email, int code) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try {
            Treinador treinador = (Treinador) em.find(Treinador.class, email);
            if (treinador == null) {
                throw new MyEntityNotFoundException("Treinador com email " + email + " não existe.");
            }
            Treino treino = em.find(Treino.class, code);
            if (treino == null) {
                throw new MyEntityNotFoundException("Treino com o codigo " + code + " não existe.");
            }
            if (treinador.getTreinosLecionados().contains(treino) || treino.getTreinador().equals(treinador)) {
                throw new MyIllegalArgumentException("Treinador já está defino para o treino " + code);
            }
            treinador.addTreinoLecionado(treino);
            treino.setTreinador(treinador);

        } catch (MyEntityNotFoundException | MyIllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_ENROLLING_TREINADOR_IN_TREINO ---->" + e.getMessage());
        }
    }

    public void unrollTreinadorFromTreino(String email, int code) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try {
            Treinador treinador = (Treinador) em.find(Treinador.class, email);
            if (treinador == null) {
                throw new MyEntityNotFoundException("Treinador com email " + email + " não existe.");
            }
            Treino treino = em.find(Treino.class, code);
            if (treino == null) {
                throw new MyEntityNotFoundException("Treino com o codigo " + code + " não existe.");
            }
            if (!treinador.getTreinosLecionados().contains(treino) || !treino.getTreinador().equals(treinador)) {
                throw new MyIllegalArgumentException("Treinador não está definido para o Treino " + code);
            }
            treinador.removeTreinoLecionado(treino);
            treino.setTreinador(null);

        } catch (MyEntityNotFoundException | MyIllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_UNROLLING_TREINADOR_FROM_TREINO ---->" + e.getMessage());
        }
    }
}
