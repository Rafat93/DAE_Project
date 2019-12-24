package ejbs;



import entities.*;
import enums.DiasSemana;
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

    public Treino create (int code, Treinador treinador, Modalidade modalidade, Graduacao graduacao, Escalao escalao, Time horaInicio, Time horaFim, DiasSemana diaSemana){
        try {
            Treino treino = new Treino(code,treinador,modalidade,graduacao,escalao,horaInicio,horaFim,diaSemana);
            em.persist(treino);
            return  treino;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public Treino update (int code, Treinador treinador, Modalidade modalidade, Graduacao graduacao, Escalao escalao, Time horaInicio, Time horaFim, DiasSemana diaSemana) throws MyEntityNotFoundException {
        try {
            Treino treino = em.find(Treino.class,code);
            if(treino == null){
                throw new MyEntityNotFoundException("Treino não encontrado!");
            }
            em.lock(treino, LockModeType.OPTIMISTIC);

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

}
