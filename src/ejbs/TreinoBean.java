package ejbs;



import entities.*;
import enums.DiasSemana;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.sql.Time;
import java.util.List;

@Stateless(name = "TreinoEJB")
public class TreinoBean {

    @PersistenceContext
    private EntityManager em;

    public Treino create (int code, String emailTreinador, String siglaModalidade, int idGraduacao, int idEscalao, Time horaInicio, Time horaFim, DiasSemana diaSemana){
        try {
            if(em.find(Treino.class, code) != null){
                throw new MyEntityAlreadyExistsException("Treino com o codigo: " + code + " já existe");
            }
            Treinador treinador = em.find(Treinador.class,emailTreinador);
            if(treinador == null){
                throw new MyEntityNotFoundException("Treinador com o email: " + emailTreinador + " não existe.");
            }
            Modalidade modalidade = em.find(Modalidade.class,siglaModalidade);
            if(modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade com a sigla: " + siglaModalidade + " não existe.");
            }
            Graduacao graduacao = em.find(Graduacao.class,idGraduacao);
            if(graduacao == null){
                throw new MyEntityNotFoundException("Graduação com o id: " + idGraduacao + " não existe.");
            }
            Escalao escalao = em.find(Escalao.class,idEscalao);
            if(escalao == null){
                throw new MyEntityNotFoundException("Escalão com o id: " + idEscalao + " não existe.");
            }
            Treino treino = new Treino(code,treinador,modalidade,graduacao,escalao,horaInicio,horaFim,diaSemana);
            em.persist(treino);
            return  treino;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public Treino update (int code, String emailTreinador, String siglaModalidade, int idGraduacao, int idEscalao, Time horaInicio, Time horaFim, DiasSemana diaSemana) throws MyEntityNotFoundException {
        try {
            Treino treino = em.find(Treino.class,code);
            if(treino == null){
                throw new MyEntityNotFoundException("Treino não encontrado!");
            }

            em.lock(treino, LockModeType.OPTIMISTIC);

            Treinador treinador = em.find(Treinador.class,emailTreinador);
            if(treinador == null){
                throw new MyEntityNotFoundException("Treinador com o email: " + emailTreinador + " não existe.");
            }
            Modalidade modalidade = em.find(Modalidade.class,siglaModalidade);
            if(modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade com a sigla: " + siglaModalidade + " não existe.");
            }
            Graduacao graduacao = em.find(Graduacao.class,idGraduacao);
            if(graduacao == null){
                throw new MyEntityNotFoundException("Graduação com o id: " + idGraduacao + " não existe.");
            }
            Escalao escalao = em.find(Escalao.class,idEscalao);
            if(escalao == null){
                throw new MyEntityNotFoundException("Escalão com o id: " + idEscalao + " não existe.");
            }


            treino.setCode(code);
            treino.setTreinador(treinador);
            treino.setModalidade(modalidade);
            treino.setGraduacao(graduacao);
            treino.setEscalao(escalao);
            treino.setHoraInicio(horaInicio);
            treino.setHoraFim(horaFim);
            treino.setDiaSemana(diaSemana);

            em.merge(treino);

            return treino;

        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR UPDATING USER", e);
        }
    }

    public List<Treino> all(){
        try{
            return (List<Treino>) em.createNamedQuery("getAllTreinos").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_TREINOS", e);
        }
    }

    public Treino findTreino(int code){
        try{
            return em.find(Treino.class,code);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_TREINO", e);
        }
    }

    public void delete(int code){
        try {
            em.remove(findTreino(code));
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_TREINO",e);
        }
    }



}
