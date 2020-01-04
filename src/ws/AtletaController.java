package ws;

import dtos.AtletaDTO;
import dtos.EmailDTO;
import dtos.ModalidadeDTO;
import ejbs.AtletaBean;
import ejbs.EmailBean;
import entities.Atleta;
import entities.Modalidade;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static utils.Utilitarios.format;

@Path("/atletas") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class AtletaController {

    @EJB
    private AtletaBean atletaBean;

    @EJB
    private EmailBean emailBean;

    @Context
    private SecurityContext securityContext;


    @GET
    @Path("/")
    @RolesAllowed({"Administrador"})
    public List<AtletaDTO> all() {
        return toDTOsNoModalidades(atletaBean.all());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrador"})
    public Response createNewAtleta (AtletaDTO atletaDTO) throws ParseException, MyEntityAlreadyExistsException {
        GregorianCalendar dataNascimento = format(atletaDTO.getDataNascimento());
        Atleta atleta = atletaBean.create(
                atletaDTO.getNumeroSocio(),
                atletaDTO.getNome(),
                atletaDTO.getEmail(),
                atletaDTO.getPassword(),
                dataNascimento.get(Calendar.DAY_OF_MONTH),
                dataNascimento.get(Calendar.MONTH),
                dataNascimento.get(Calendar.YEAR),
                atletaDTO.getNumIdentificacaoCivil(),
                atletaDTO.getNumContribuinte(),
                atletaDTO.getMorada());
        return Response.status(Response.Status.CREATED).entity(atletaToDTO(atleta)).build();
    }

    @PUT
    @Path("{email}")
    public Response updateAtleta(@PathParam("email") String email, AtletaDTO atletaDTO)
            throws MyEntityNotFoundException, ParseException {
        Principal principal = securityContext.getUserPrincipal();
        System.out.println(email + " --- " + principal.getName());

        if(securityContext.isUserInRole("Administrador") ||
                securityContext.isUserInRole("Atleta") && principal.getName().equals(atletaDTO.getNome())) {
            GregorianCalendar dataNascimento = format(atletaDTO.getDataNascimento());
            atletaBean.update(atletaDTO.getNumeroSocio(),
                    atletaDTO.getNome(),
                    email,
                    atletaDTO.getPassword(),
                    dataNascimento.get(Calendar.DAY_OF_MONTH),
                    dataNascimento.get(Calendar.MONTH),
                    dataNascimento.get(Calendar.YEAR),
                    atletaDTO.getNumIdentificacaoCivil(),
                    atletaDTO.getNumContribuinte(),
                    atletaDTO.getMorada());
            Atleta atleta = atletaBean.findAtleta(email);
            return Response.status(Response.Status.OK)
                    .entity(atletaToDTO(atleta))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("{email}")
    @RolesAllowed({"Administrador"})
    public Response removeAtleta(@PathParam("email") String email)
            throws MyEntityNotFoundException {
        atletaBean.delete(email);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{email}")
    public Response getAtletaDetails (@PathParam("email")String email) throws MyEntityAlreadyExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Principal principal = securityContext.getUserPrincipal();
        System.out.println(email + " --- " + principal.getName());

        Atleta atleta = atletaBean.findAtleta(email);
        if(securityContext.isUserInRole("Administrador") ||
                securityContext.isUserInRole("Atleta") && principal.getName().equals(email)) {

            return Response.status(Response.Status.OK)
                    .entity(atletaToDTO(atleta))
                    .build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{email}/modalidades")
    public Response getAtletaModalidades(@PathParam("email") String email){
        Atleta atleta = atletaBean.findAtleta(email);
        if (atleta != null){
            GenericEntity<List<ModalidadeDTO>> entity
                    = new GenericEntity<List<ModalidadeDTO>>(modalidadeToDTOs(atleta.getModalidades())){

            };
            return  Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Atleta with email "+email+ " not found")
                .build();
    }

    @PUT
    @Path("{email}/modalidade/enroll/{sigla}")
    @RolesAllowed({"Administrator"})
    public Response enrollAtletaInModalidade(@PathParam("email") String email, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        atletaBean.enrollAtletaInModalidade(email, sigla);
        return getAtletaModalidades(email);
    }

    @PUT
    @Path("{email}/modalidade/unroll/{sigla}")
    @RolesAllowed({"Administrator"})
    public Response unrollAtletaFromModalidade(@PathParam("email") String email, @PathParam("sigla") String sigla)throws MyEntityNotFoundException, MyIllegalArgumentException {
        atletaBean.unrollAtletaFromModalidade(email, sigla);
        return getAtletaModalidades(email);
    }

    @POST
    @Path("{email}/email/send")
    public Response sendEmailToAtleta(@PathParam("email") String email, EmailDTO emailDTO) throws MessagingException {
        Atleta atleta = atletaBean.findAtleta(email);
        if (atleta != null) {
            emailBean.send(atleta.getEmail(), emailDTO.getSubject(), emailDTO.getMessage());
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity("Atleta with email " + email + " not found.").
                build();
    }

    @GET
    @Path("{email}/modalidades_livres")
    public Response getModalidadesLivres(@PathParam("email")String email) throws MyEntityNotFoundException {
        List <Modalidade> modalidades = atletaBean.getModalidadesSemAtleta(email);
        return  Response.status(Response.Status.OK)
                .entity(modalidadeToDTOs(modalidades))
                .build();
    }


    // Converts an entity Atleta to a DTO Atleta class
    AtletaDTO atletaToDTO(Atleta atleta){
        AtletaDTO atletaDTO = new AtletaDTO(
                atleta.getNumeroSocio(),
                atleta.getNome(),
                atleta.getEmail(),
                atleta.getPassword(),
                atleta.getDataNascimento(),
                atleta.getNumIdentificacaoCivil(),
                atleta.getNumContribuinte(),
                atleta.getMorada()
        );

        List < ModalidadeDTO> modalidadesDTO = modalidadeToDTOs(atleta.getModalidades());
        atletaDTO.setModalidades(modalidadesDTO);

        return atletaDTO;
    }

    AtletaDTO toDTONoModalidades(Atleta atleta){
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


    ModalidadeDTO atletaToDTO(Modalidade modalidade){
        return new ModalidadeDTO(modalidade.getSigla(),
                modalidade.getNome(),
                modalidade.getEpocaDesportiva(),
                modalidade.getQuotaAnual());
    }

    // converts an entire list of Atletas entities without the list of Modalidades into a list of DTOs
    List <AtletaDTO> toDTOsNoModalidades (List<Atleta> atletas){
        return atletas.stream().map(this::toDTONoModalidades).collect(Collectors.toList());
    }

    List<ModalidadeDTO> modalidadeToDTOs(Collection<Modalidade> modalidades){
        return modalidades.stream().map(this::atletaToDTO).collect(Collectors.toList());
    }
}
