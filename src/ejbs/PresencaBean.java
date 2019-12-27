package ejbs;

import entities.Atleta;
import entities.Presenca;
import entities.Treinador;
import entities.Treino;
import exceptions.MyEntityAlreadyExistsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
            List <Atleta> atletas = geraAtletasPresentes(emailsAtletas);
            presenca = new Presenca(code,(Treino) em.find(Treino.class,codeTreino),new GregorianCalendar(dia,mes,ano),(Treinador) em.find(Treinador.class,emailTreinador));
            presenca.setAtletasPresentes(atletas);
            em.persist(presenca);
            return presenca;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
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
