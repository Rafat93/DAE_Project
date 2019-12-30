package ejbs;

import dtos.EmailDTO;
import entities.Inscricao;
import entities.Socio;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Stateless(name = "InscricaoEJB")
public class InscricaoBean {

    @EJB
    private SocioBean socioBean;

    @EJB
    private EmailBean emailBean;

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

    public void confirmInscricao(String code) throws MyEntityNotFoundException {
        try{
            Inscricao inscricao = em.find(Inscricao.class,code);
            if(inscricao == null){
                throw new MyEntityNotFoundException("Inscricao com o codigo "+code+" não existe");
            }
            //cria socio
            socioBean.create(socioBean.all().size()+1,inscricao.getNome(),Long.toString(inscricao.getNumContribuinte()),
                    inscricao.getEmail(),inscricao.getDataNascimento().get(Calendar.DAY_OF_MONTH),inscricao.getDataNascimento().get(Calendar.MONTH),inscricao.getDataNascimento().get(Calendar.YEAR),
                    inscricao.getNumIdentificacaoCivil(),inscricao.getNumContribuinte(),inscricao.getMorada());
            //envia email a informar mail e password
            EmailDTO emailDTO = new EmailDTO("Inscrição Confirmada",
                    "A sua incrição foi aceite.\n" +
                                "Para aceder à sua conta utilize o seu email, "+ inscricao.getEmail()+", e o seu número de contribuinte como password.\n" +
                                "Deve alterar a sua password assim que fizer o login pela primeira vez para sua segurança.");
            emailBean.send(inscricao.getEmail(),emailDTO.getSubject(),emailDTO.getMessage());
            //confirma inscrição
            inscricao.setConfirmed(true);
            em.merge(inscricao);
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR CONFIRMING INSCRICAO", e);
        }
    }

    public List <Inscricao> all(){
        try{
            return (List<Inscricao>) em.createNamedQuery("getAllInscricoes").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_INSCRICOES", e);
        }
    }

    public List <Inscricao> allNotConfirmed(){
        try{
            return (List<Inscricao>) em.createNamedQuery("getInscricoesNotConfirmed").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_INSCRICOES_NOT_CONFIRMED", e);
        }
    }

    public Inscricao findInscricao(String code) {
        try{
            return em.find(Inscricao.class,code);
        }catch (Exception e){
            throw new EJBException("ERROR_FINDING_INSCRICAO", e);
        }
    }

    public void delete (String code) throws MyEntityNotFoundException {
        try{
            Inscricao inscricao = em.find(Inscricao.class,code);
            if(inscricao == null){
                throw new MyEntityNotFoundException("Inscrição com o codigo " + code + " não existe");
            }
            em.remove(findInscricao(code));
            Socio socio = em.find(Socio.class,inscricao.getEmail());
            if(socio != null){
                em.remove(socio);
            }
        }catch (MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_ATLETA",e);
        }
    }
}
