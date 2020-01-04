package ws;

import dtos.InscricaoDTO;
import ejbs.InscricaoBean;
import entities.Inscricao;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import static utils.Utilitarios.format;

@Path("/inscricoes") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class InscricaoController {

    @EJB
    private InscricaoBean inscricaoBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public List <InscricaoDTO> all(){
        return toDTOs(inscricaoBean.all());
    }

    @GET
    @Path("/pendentes")
    public List <InscricaoDTO> allNotConfirmed(){
        return toDTOs(inscricaoBean.allNotConfirmed());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrador"})
    public Response createNewInscricao (InscricaoDTO inscricaoDTO) throws MyEntityAlreadyExistsException, ParseException {
        System.out.println(inscricaoDTO.getDataNascimento());
        GregorianCalendar dataNascimento = format(inscricaoDTO.getDataNascimento());
        inscricaoBean.create(
                inscricaoDTO.getCode(),
                inscricaoDTO.getNome(),
                inscricaoDTO.getEmail(),
                dataNascimento.get(Calendar.DAY_OF_MONTH),
                dataNascimento.get(Calendar.MONTH),
                dataNascimento.get(Calendar.YEAR),
                inscricaoDTO.getNumIdentificacaoCivil(),
                inscricaoDTO.getNumContribuinte(),
                inscricaoDTO.getMorada()
        );
        Inscricao inscricao = inscricaoBean.findInscricao(inscricaoDTO.getCode());
        return Response.status(Response.Status.CREATED).entity(toDTO(inscricao)).build();
    }

    @PUT
    @Path("{code}/confirm")
    @RolesAllowed({"Administrador"})
    public Response confirmInscricao (@PathParam("code")String code) throws MyEntityNotFoundException {
        inscricaoBean.confirmInscricao(code);
        Inscricao inscricao = inscricaoBean.findInscricao(code);
        return Response.status(Response.Status.OK).entity(toDTO(inscricao)).build();
    }

    @GET
    @Path("{code}")
    @RolesAllowed({"Administrador"})
    public Response getInscricaoDetails (@PathParam("code")String code){
        Inscricao inscricao = inscricaoBean.findInscricao(code);
        return Response.status(Response.Status.OK)
                .entity(toDTO(inscricao))
                .build();
    }

    @DELETE
    @Path("{code}")
    @RolesAllowed({"Administrador"})
    public Response removeInscricao(@PathParam("code") String code)
            throws MyEntityNotFoundException {
        inscricaoBean.delete(code);
        return Response.status(Response.Status.OK).build();
    }

    InscricaoDTO toDTO(Inscricao inscricao){
        return new InscricaoDTO(
          inscricao.getCode(),
          inscricao.getNome(),
          inscricao.getDataNascimento(),
          inscricao.getNumIdentificacaoCivil(),
          inscricao.getNumContribuinte(),
          inscricao.getMorada(),
          inscricao.getEmail()
        );
    }

    List <InscricaoDTO> toDTOs (List <Inscricao> inscricoes){
        return inscricoes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
