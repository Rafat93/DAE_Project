package ejbs;

import entities.*;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Stateless(name = "InscricaoAtletaEJB")
public class InscricaoAtletaModalidadeBean {

    @EJB
    private ModalidadeBean modalidadeBean;

    @EJB
    private TreinoBean treinoBean;

    @PersistenceContext
    private EntityManager em;

    public void create (String code, String siglaModalidade, List <String> codeTreinos) throws MyEntityAlreadyExistsException {
        try{
            InscricaoAtletaModalidade inscricao = em.find(InscricaoAtletaModalidade.class,code);
            if (inscricao != null){
                throw new MyEntityAlreadyExistsException("Inscrição com o codigo "+code+"já existe");
            }
            Modalidade modalidade = em.find(Modalidade.class,siglaModalidade);
            if(codeTreinos.size() == 0){
                throw new MyIllegalArgumentException("Inscrição de atletas tem de ter treinos associados");
            }
            inscricao = new InscricaoAtletaModalidade(code,modalidade,geraTreinos(codeTreinos));
            em.persist(inscricao);
        }catch (MyEntityAlreadyExistsException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private Set <Treino> geraTreinos(List<String> codeTreinos) {
        Set< Treino> treinos = new HashSet<>();
        for (String code : codeTreinos){
            treinos.add(treinoBean.findTreino(code));
        }
        return treinos;
    }

    private Set <Modalidade> geraModalidades(List <String> siglas){
        Set <Modalidade> modalidades = new HashSet<>();
        for (String sigla : siglas){
            modalidades.add(modalidadeBean.findModalidade(sigla));
        }
        return modalidades;
    }
}
