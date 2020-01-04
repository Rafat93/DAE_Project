package ejbs;

import dtos.EmailDTO;
import entities.*;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
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
    private EmailBean emailBean;

    @EJB
    private AtletaBean atletaBean;

    @EJB
    private SocioBean socioBean;

    @EJB
    private TreinoBean treinoBean;

    @PersistenceContext
    private EntityManager em;

    public void create (String code, String email, String siglaModalidade, List <String> codeTreinos) throws MyEntityAlreadyExistsException {
        try{
            InscricaoAtletaModalidade inscricao = em.find(InscricaoAtletaModalidade.class,code);
            if (inscricao != null){
                throw new MyEntityAlreadyExistsException("Inscrição com o codigo "+code+"já existe");
            }
            Modalidade modalidade = em.find(Modalidade.class,siglaModalidade);
            if (modalidade == null) {
                throw new MyEntityNotFoundException("Modalidade with code " + siglaModalidade + " not found.");
            }
            Socio socio = em.find(Socio.class,email);
            if (socio == null) {
                throw new MyEntityNotFoundException("Socio não encontrado");
            }
            if(codeTreinos.size() == 0){
                for(Treino treino:modalidade.getTreinos())
                    codeTreinos.add(treino.getCode());
                //throw new MyIllegalArgumentException("Inscrição de atletas tem de ter treinos associados");
            }
            inscricao = new InscricaoAtletaModalidade(
                    code,
                    socio.getNome(),
                    socio.getEmail(),
                    socio.getDataNascimento(),
                    socio.getNumIdentificacaoCivil(),
                    socio.getNumContribuinte(),
                    socio.getMorada(),
                    modalidade,
                    geraTreinos(codeTreinos));

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

    public void confirmInscricao(String code) throws MyEntityNotFoundException {
        try{
            InscricaoAtletaModalidade inscricao = em.find(InscricaoAtletaModalidade.class,code);
            if(inscricao == null){
                throw new MyEntityNotFoundException("Inscricao com o codigo "+code+" não existe");
            }
            Atleta atleta = em.find(Atleta.class,inscricao.getEmail());
            //atleta já fez uma inscricao modalidade
            if(atleta != null){
                atletaBean.enrollAtletaInModalidade(atleta.getEmail(),inscricao.getModalidade().getSigla());
            }else {
                Socio socio = em.find(Socio.class, inscricao.getEmail());
                long numeroSocio = socio.getNumeroSocio();
                String password = socio.getPassword();
                socioBean.delete(socio.getEmail());
                //cria atleta
                atletaBean.create(
                        numeroSocio,
                        inscricao.getNome(),
                        inscricao.getEmail(),
                        password,
                        inscricao.getDataNascimento().get(Calendar.DAY_OF_MONTH),
                        inscricao.getDataNascimento().get(Calendar.MONTH),
                        inscricao.getDataNascimento().get(Calendar.YEAR),
                        inscricao.getNumIdentificacaoCivil(),
                        inscricao.getNumContribuinte(),
                        inscricao.getMorada());
            }
            //envia email a informar que a inscrição como atleta na modalidade foi aceite
            EmailDTO emailDTO = new EmailDTO("Inscrição na Modalidade "+inscricao.getModalidade().getNome()+" Confirmada",
                    "A sua incrição na Modalidade "+inscricao.getModalidade().getNome()+"foi aceite.");
            emailBean.send(inscricao.getEmail(),emailDTO.getSubject(),emailDTO.getMessage());
            //confirma inscrição
            inscricao.setConfirmed(true);
            em.merge(inscricao);
        }catch(MyEntityNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR_FINDING_INSCRICAO", e);
        }
    }

    public InscricaoAtletaModalidade findInscricao(String code){
        try{
            return em.find(InscricaoAtletaModalidade.class,code);
        }catch(Exception e){
            throw new EJBException("ERROR CONFIRMING INSCRICAO ATLETA", e);
        }
    }
}
