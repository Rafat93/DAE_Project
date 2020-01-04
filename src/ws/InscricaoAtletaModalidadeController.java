package ws;

import dtos.InscricaoAtletaModalidadeDTO;
import dtos.InscricaoDTO;
import ejbs.InscricaoAtletaModalidadeBean;
import entities.Inscricao;
import entities.InscricaoAtletaModalidade;
import entities.Treino;
import exceptions.MyEntityAlreadyExistsException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.ParseException;
import java.util.*;


@Path("/inscricoes/atleta") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class InscricaoAtletaModalidadeController {

    @EJB
    private InscricaoAtletaModalidadeBean inscricaoAtletaModalidadeBean;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/")
    @RolesAllowed({"Administrador"})
    public Response createNewInscricaoAtleta (InscricaoAtletaModalidadeDTO inscricaoDTO) throws MyEntityAlreadyExistsException, ParseException {
        inscricaoAtletaModalidadeBean.create(
                inscricaoDTO.getCode(),
                inscricaoDTO.getEmail(),
                inscricaoDTO.getSiglaModalidade(),
                inscricaoDTO.getTreinos()
        );
        InscricaoAtletaModalidade inscricao = inscricaoAtletaModalidadeBean.findInscricao(inscricaoDTO.getCode());
        return Response.status(Response.Status.CREATED).entity(toDTO(inscricao)).build();
    }

    InscricaoAtletaModalidadeDTO toDTO(InscricaoAtletaModalidade inscricaoAtletaModalidade){
        InscricaoAtletaModalidadeDTO inscricaoAtletaModalidadeDTO = new InscricaoAtletaModalidadeDTO(
                inscricaoAtletaModalidade.getCode(),
                inscricaoAtletaModalidade.getEmail(),
                inscricaoAtletaModalidade.getModalidade().getSigla()
        );
        inscricaoAtletaModalidadeDTO.setTreinos(codigosTreinos(inscricaoAtletaModalidade.getTreinos()));
        return inscricaoAtletaModalidadeDTO;
    }

    List<String> codigosTreinos(Set<Treino> treinos){
        List <String> codeTreinos = new LinkedList<>();
        for(Treino treino : treinos){
            codeTreinos.add(treino.getCode());
        }
        return codeTreinos;
    }
}
