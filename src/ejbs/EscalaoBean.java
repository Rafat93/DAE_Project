package ejbs;

import entities.Escalao;
import entities.Modalidade;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "EscalaoEJB")
public class EscalaoBean {

    @PersistenceContext
    private EntityManager em;

    public List<Escalao> all(){
        try{
            return (List<Escalao>) em.createNamedQuery("getAllEscaloes").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ESCALOES", e);
        }
    }

    public Escalao create (String code, String nome, int idadeMin, int idadeMax, String sigla) throws MyEntityAlreadyExistsException, MyEntityNotFoundException {
        try{
            if(idadeMax < idadeMin){
                throw new NumberFormatException("Idade Maxima não podeser inferior à idade minima");
            }
            Escalao escalao = em.find(Escalao.class, code);
            if(escalao != null){
                throw new MyEntityAlreadyExistsException("Escalão com o codigo "+code+" já existe");
            }
            Modalidade modalidade = em.find(Modalidade.class, sigla);
            if(modalidade == null){
                throw new MyEntityNotFoundException("Modalidade com a sigla" + sigla + " não existe.");
            }
            escalao = new Escalao(code,nome,idadeMin,idadeMax,modalidade);
            em.persist(escalao);
            return escalao;
        }catch (MyEntityAlreadyExistsException | MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void enrollEscalaoInModalidade( String code, String sigla) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Escalao escalao = em.find(Escalao.class, code);
            if(escalao == null){
                throw new MyEntityNotFoundException("Escalão com o codigo "+code+" não existe");
            }
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,sigla);
            if (modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade with code " + sigla + " not found.");
            }
            if(modalidade.getEscaloes().contains(escalao)){
                throw new MyIllegalArgumentException("Escalão is already enrolled in modalidade with code " + sigla);
            }
            modalidade.addEscalao(escalao);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e) {
            throw  e;
        } catch (Exception e) {
            throw new EJBException("ERROR_ENROLLING_ESCALAO_IN_MODALIDADE ---->" + e.getMessage());
        }
    }

    public void unrollEscalaoFromModalidade( String code, String sigla) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Escalao escalao = em.find(Escalao.class, code);
            if(escalao == null){
                throw new MyEntityNotFoundException("Escalão com o codigo "+code+" não existe");
            }
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,sigla);
            if (modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade with code " + sigla + " not found.");
            }
            if(!modalidade.getEscaloes().contains(escalao)){
                throw new MyIllegalArgumentException("Escalão is already unrolled from modalidade with code " + sigla);
            }
            modalidade.removeEscalao(escalao);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e) {
            throw  e;
        } catch (Exception e) {
            throw new EJBException("ERROR_UNROLLING_ESCALAO_FROM_MODALIDADE ---->" + e.getMessage());
        }
    }

    public void delete (String code){
        try{
            em.remove(findEscalao(code));
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_ESCALAO");
        }
    }

    public Escalao findEscalao (String code) {
        try{
            return em.find(Escalao.class,code);
        }catch (Exception e){
            throw new EJBException("ERROR_FINDING_ESCALAO", e);
        }
    }
}
