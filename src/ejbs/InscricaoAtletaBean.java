package ejbs;

import entities.Inscricao;
import exceptions.MyEntityAlreadyExistsException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.GregorianCalendar;

@Stateless(name = "InscricaoAtletaEJB")
public class InscricaoAtletaBean {

    @EJB
    private InscricaoAtletaBean inscricaoAtletaBean;

    @PersistenceContext
    private EntityManager em;

    public void create (String code, String nome, String email, int dia, int mes, int ano, long numIdentificacaoCivil, long numContibuinte, String morada) throws MyEntityAlreadyExistsException {
        try{
            Inscricao inscricao = em.find(Inscricao.class,code);
            if (inscricao != null){
                throw new MyEntityAlreadyExistsException("Inscrição com o codigo "+code+"já existe");
            }
            GregorianCalendar dataNascimento = new GregorianCalendar(ano,mes,dia);
            inscricao = new Inscricao(code,nome,email,dataNascimento,numIdentificacaoCivil,numContibuinte,morada);
            em.persist(inscricao);
        }catch (MyEntityAlreadyExistsException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
