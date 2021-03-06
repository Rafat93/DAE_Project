package ws;

import dtos.*;
import ejbs.ModalidadeBean;
import entities.*;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/modalidades") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ModalidadeController {

    @EJB
    private ModalidadeBean modalidadeBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public List<ModalidadeDTO> all() {
        return toDTOsNoLists(modalidadeBean.all());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrador"})
    public Response createNewModalidade (ModalidadeDTO modalidadeDTO) throws MyEntityAlreadyExistsException {
         modalidadeBean.create(
                 modalidadeDTO.getSigla(),
                 modalidadeDTO.getNome(),
                 modalidadeDTO.getEpocaDesportiva(),
                 modalidadeDTO.getQuotaAnual());
         Modalidade modalidade = modalidadeBean.findModalidade(modalidadeDTO.getSigla());
        return Response.status(Response.Status.CREATED)
                .entity(toDTONoLists(modalidade))
                .build();
    }

    @PUT
    @Path("{sigla}")
    @RolesAllowed({"Administrador"})
    public Response updateModalidade(@PathParam("sigla")String sigla,ModalidadeDTO modalidadeDTO) throws MyEntityNotFoundException {
        modalidadeBean.update(
                modalidadeDTO.getSigla(),
                modalidadeDTO.getNome(),
                modalidadeDTO.getEpocaDesportiva(),
                modalidadeDTO.getQuotaAnual());
        Modalidade modalidade = modalidadeBean.findModalidade(sigla);
        return  Response.status(Response.Status.OK)
                .entity(toDTONoLists(modalidade))
                .build();
    }

    @DELETE
    @Path("{sigla}")
    @RolesAllowed({"Administrador"})
    public Response removeModalidade (@PathParam("sigla")String sigla) throws MyEntityNotFoundException {
        modalidadeBean.delete(sigla);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{sigla}")
    @RolesAllowed({"Administrador"})
    public Response getModalidadeDetails (@PathParam("sigla")String sigla){
        Modalidade modalidade = modalidadeBean.findModalidade(sigla);
        return Response.status(Response.Status.OK)
                .entity(toDTO(modalidade))
                .build();
    }

    @GET
    @Path("{sigla}/atletas")
    public Response getModalidadeAtletas (@PathParam("sigla")String sigla){
        Modalidade modalidade = modalidadeBean.findModalidade(sigla);
        if(modalidade != null){
            GenericEntity<List<AtletaDTO>> entity
                    = new GenericEntity<List<AtletaDTO>>(atletaToDTOs(modalidade.getAtletas())){
            };
            return  Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Modalidade com a "+ sigla + " não existe")
                .build();
    }


    @GET
    @Path("{sigla}/treinadores")
    public Response getModalidadeTreinadores (@PathParam("sigla")String sigla){
        Modalidade modalidade = modalidadeBean.findModalidade(sigla);
        if(modalidade != null){
            GenericEntity<List<TreinadorDTO>> entity
                    = new GenericEntity<List<TreinadorDTO>>(treinadoresToDTOs(modalidade.getTreinadores())){
            };
            return  Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Modalidade com a "+ sigla + " não existe")
                .build();
    }

    @GET
    @Path("{sigla}/escaloes")
    public Response getModalidadeEscaloes (@PathParam("sigla")String sigla){
        Modalidade modalidade = modalidadeBean.findModalidade(sigla);
        if(modalidade != null){
            GenericEntity<List<EscalaoDTO>> entity
                    = new GenericEntity<List<EscalaoDTO>>(escalaoToDTOs(modalidade.getEscaloes())){
            };
            return  Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Modalidade com a "+ sigla + " não existe")
                .build();
    }

    @GET
    @Path("{sigla}/treinos")
    public Response getModalidadeTreinos (@PathParam("sigla")String sigla){
        Modalidade modalidade = modalidadeBean.findModalidade(sigla);
        if(modalidade != null){
            GenericEntity<List<TreinoDTO>> entity
                    = new GenericEntity<List<TreinoDTO>>(treinoToDTOs(modalidade.getTreinos())){
            };
            return  Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Modalidade com a "+ sigla + " não existe")
                .build();
    }

    @GET
    @Path("{sigla}/graduacoes")
    public Response getModalidadeGraduacoes (@PathParam("sigla")String sigla){
        Modalidade modalidade = modalidadeBean.findModalidade(sigla);
        if(modalidade != null){
            GenericEntity<List<GraduacaoDTO>> entity
                    = new GenericEntity<List<GraduacaoDTO>>(graduacaoToDTOs(modalidade.getGraduacoes())){
            };
            return  Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Modalidade com a "+ sigla + " não existe")
                .build();
    }

    @GET
    @Path("{sigla}/treinadores_livres")
    @RolesAllowed({"Administrador"})
    public Response getTreinadoresLivres(@PathParam("sigla")String sigla) throws MyEntityNotFoundException {
        List <Treinador> treinadores = modalidadeBean.getTreinadoresSemModalidade(sigla);
        return  Response.status(Response.Status.OK)
                .entity(treinadoresToDTOs(treinadores))
                .build();
    }

    ModalidadeDTO toDTO (Modalidade modalidade){
        ModalidadeDTO modalidadeDTO = new ModalidadeDTO(
                modalidade.getSigla(),
                modalidade.getNome(),
                modalidade.getEpocaDesportiva(),
                modalidade.getQuotaAnual()
        );
        List <AtletaDTO> atletasDTO = atletaToDTOs(modalidade.getAtletas());
        List <EscalaoDTO> escaloesDTO = escalaoToDTOs(modalidade.getEscaloes());
        List <GraduacaoDTO> graduacoesDTO = graduacaoToDTOs(modalidade.getGraduacoes());
        List <TreinoDTO> treinosDTO = treinoToDTOs(modalidade.getTreinos());
        List <TreinadorDTO> treinadoresDTO = treinadoresToDTOs(modalidade.getTreinadores());

        modalidadeDTO.setAtletas(atletasDTO);
        modalidadeDTO.setEscaloes(escaloesDTO);
        modalidadeDTO.setTreinos(treinosDTO);
        modalidadeDTO.setTreinadores(treinadoresDTO);
        modalidadeDTO.setGraduacoes(graduacoesDTO);
        return modalidadeDTO;
    }

    ModalidadeDTO toDTONoLists (Modalidade modalidade){
        return new ModalidadeDTO(
                modalidade.getSigla(),
                modalidade.getNome(),
                modalidade.getEpocaDesportiva(),
                modalidade.getQuotaAnual()
        );
    }

    List <ModalidadeDTO> toDTOsNoLists (Collection <Modalidade> modalidades){
        return modalidades.stream().map(this::toDTONoLists).collect(Collectors.toList());
    }

    AtletaDTO atletaToDTO(Atleta atleta){
        return new AtletaDTO(
                atleta.getNumeroSocio(),
                atleta.getNome(),
                atleta.getEmail(),
                atleta.getPassword(),
                atleta.getDataNascimento(),
                atleta.getNumIdentificacaoCivil(),
                atleta.getNumContribuinte(),
                atleta.getMorada()
        );
    }
    List <AtletaDTO> atletaToDTOs (List<Atleta> atletas){
        return atletas.stream().map(this::atletaToDTO).collect(Collectors.toList());
    }

    TreinoDTO treinoToDTO (Treino treino){
        return new TreinoDTO(
                treino.getCode(),
                treino.getTreinador().getNome(),
                treino.getModalidade().getSigla(),
                treino.getGraduacao().getCode(),
                treino.getEscalao().getCode(),
                treino.getHoraInicio(),
                treino.getHoraFim(),
                treino.getDiaSemana()
        );
    }

    List <TreinoDTO> treinoToDTOs (List <Treino> treinos){
        return treinos.stream().map(this::treinoToDTO).collect(Collectors.toList());
    }

    TreinadorDTO treinadorToDTO(Treinador treinador){
        return new TreinadorDTO(
                treinador.getNumeroCedula(),
                treinador.getNome(),
                treinador.getEmail(),
                treinador.getPassword()
        );
    }

    List<TreinadorDTO> treinadoresToDTOs (List<Treinador> treinador){
        return treinador.stream().map(this::treinadorToDTO).collect(Collectors.toList());
    }

    SocioDTO socioToDTO(Socio socio){
        return new SocioDTO(
                socio.getNumeroSocio(),
                socio.getNome(),
                socio.getEmail(),
                socio.getPassword(),
                socio.getDataNascimento(),
                socio.getNumIdentificacaoCivil(),
                socio.getNumContribuinte(),
                socio.getMorada()
        );
    }

    List<SocioDTO> socioToDTOs(List<Socio> socios) {
        return socios.stream().map(this::socioToDTO).collect(Collectors.toList());

    }

    EscalaoDTO escalaoToDTO (Escalao escalao){
        return new EscalaoDTO(
                escalao.getCode(),
                escalao.getNome(),
                escalao.getIdadeMin(),
                escalao.getIdadeMax(),
                escalao.getModalidade().getSigla()
        );
    }

    List <EscalaoDTO> escalaoToDTOs (List <Escalao> escaloes){
        return escaloes.stream().map(this::escalaoToDTO).collect(Collectors.toList());
    }

    GraduacaoDTO graduacaoToDTO (Graduacao graduacao){
        return new GraduacaoDTO(
                graduacao.getCode(),
                graduacao.getNome(),
                graduacao.getModalidade().getSigla());
    }

    List <GraduacaoDTO> graduacaoToDTOs (List <Graduacao> graduacoes){
        return graduacoes.stream().map(this::graduacaoToDTO).collect(Collectors.toList());
    }
}
