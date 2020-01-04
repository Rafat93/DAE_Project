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

    public void create (String code, String nome, String email, int dia, int mes, int ano, long numIdentificacaoCivil, long numContibuinte, String morada, List<String> siglaModalidades, List <String> codeTreinos) throws MyEntityAlreadyExistsException {
        try{
            InscricaoAtleta inscricao = em.find(InscricaoAtleta.class,code);
            if (inscricao != null){
                throw new MyEntityAlreadyExistsException("Inscrição com o codigo "+code+"já existe");
            }
            if(siglaModalidades.size() == 0){
                throw new MyIllegalArgumentException("Inscrição de atletas tem de ter modalidades associadas");
            }
            if(codeTreinos.size() == 0){
                throw new MyIllegalArgumentException("Inscrição de atletas tem de ter treinos associados");
            }
            GregorianCalendar dataNascimento = new GregorianCalendar(ano,mes,dia);
            inscricao = new InscricaoAtleta(code,nome,email,dataNascimento,numIdentificacaoCivil,numContibuinte,morada,geraModalidades(siglaModalidades),geraTreinos(codeTreinos));
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
