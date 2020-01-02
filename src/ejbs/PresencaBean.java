package ejbs;

import entities.Atleta;
import entities.Presenca;
import entities.Treinador;
import entities.Treino;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

@Stateless(name = "PresencaEJB")
public class PresencaBean {

    @PersistenceContext
    private EntityManager em;

    public Presenca create(String code, String codeTreino, int dia, int mes, int ano, List<String> emailsAtletas, String emailTreinador){
        try{
            Presenca presenca = em.find(Presenca.class,code);
            if(presenca != null){
                throw new MyEntityAlreadyExistsException("Presenca com o codigo: " + code + " já existe");
            }
            Treinador treinador = em.find(Treinador.class,emailTreinador);
            if(treinador == null){
                throw new MyEntityNotFoundException("Treinador com o email: " + emailTreinador + " não existe.");
            }
            Treino treino = em.find(Treino.class,codeTreino);
            if(treino == null){
                throw new MyEntityNotFoundException("Treino com o codigo: " + codeTreino + " não existe.");
            }
            List <Atleta> atletas = geraAtletasPresentes(emailsAtletas);
            presenca = new Presenca(code,treino,new GregorianCalendar(dia,mes,ano),treinador);
            presenca.setAtletasPresentes(atletas);
            em.persist(presenca);
            return presenca;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public Presenca update (String code, String codeTreino, int dia, int mes, int ano,List <String> emailsAtletas, String emailTreinador) throws MyEntityNotFoundException {
        try{
            Presenca presenca = em.find(Presenca.class,code);
            if(presenca == null){
                throw new MyEntityNotFoundException("Presença não encontrada!");
            }
            em.lock(presenca, LockModeType.OPTIMISTIC);

            Treinador treinador = em.find(Treinador.class,emailTreinador);
            if(treinador == null){
                throw new MyEntityNotFoundException("Treinador com o email: " + emailTreinador + " não existe.");
            }
            Treino treino = em.find(Treino.class,codeTreino);
            if(treino == null){
                throw new MyEntityNotFoundException("Treino com o codigo: " + codeTreino + " não existe.");
            }
            List <Atleta> atletas = geraAtletasPresentes(emailsAtletas);

            presenca.setCode(code);
            presenca.setTreinador(treinador);
            presenca.setTreino(treino);
            presenca.setDataTreino(new GregorianCalendar(dia,mes,ano));
            presenca.setAtletasPresentes(atletas);

            em.merge(presenca);

            return presenca;
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR UPDATING PRESENCA", e);
        }
    }

    public List <Presenca> all(){
        try{
            return (List<Presenca>) em.createNamedQuery("getAllPresencas").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRESENCAS", e);
        }
    }

    public Presenca findPresenca (String code){
        try{
            return em.find(Presenca.class,code);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_PRESENCA", e);
        }
    }

    public void delete(String code) throws MyEntityNotFoundException{
        try {
            Presenca presenca = em.find(Presenca.class,code);
            if (presenca == null) {
                throw new MyEntityNotFoundException("Presenca com o codigo: " + code + " não existe");
            }
            em.remove(findPresenca(code));
        }catch (MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_PRESENCA",e);
        }
    }

    public List <Presenca> findPresencasTreino(String codeTreino) throws MyEntityNotFoundException {
        try{
            Treino treino = em.find(Treino.class,codeTreino);
            if(treino==null){
                throw new MyEntityNotFoundException("Treino com o codigo: " + codeTreino + " não existe");

            }
            List <Presenca> presencas = this.all();
            List <Presenca> presencasTreino = new LinkedList<>();
            for(Presenca presenca:presencas){
                if(presenca.getTreino().equals(treino)){
                    presencasTreino.add(presenca);
                }
            }
            return presencasTreino;
        }catch (MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e){
            throw new EJBException("ERROR_FINDING_PRESENCAS_FROM_TREINO",e);
        }
    }

    private List<Atleta> geraAtletasPresentes(List<String> emailsAtletas) {
        List<Atleta> atletas = new LinkedList<>();
        Atleta atleta = null;
        for (String email:emailsAtletas) {
            atleta = em.find(Atleta.class,email);
            if(atleta != null){
                atletas.add(atleta);
            }
        }
        return atletas;
    }
}
