package dtos;

import java.io.Serializable;
import java.util.*;

public class SocioDTO implements Serializable {

    private long numeroSocio;

    private String nome;

    private String email;

    private String password;

    private GregorianCalendar dataNascimento;

    private List<ModalidadeDTO> modalidades;

    private long numIdentificacaoCivil;

    private long numContribuinte;

    private String morada;

    public SocioDTO() {
        modalidades = new LinkedList<>();
    }

    public SocioDTO(long numeroSocio, String nome, String email, String password, GregorianCalendar dataNascimento,long numIdentificacaoCivil, long numContribuinte, String morada) {
        this();
        this.numeroSocio = numeroSocio;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.dataNascimento = dataNascimento;
        this.numIdentificacaoCivil=numIdentificacaoCivil;
        this.numContribuinte =numContribuinte;
        this.morada=morada;
    }

    public long getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(long numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GregorianCalendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(GregorianCalendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<ModalidadeDTO> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<ModalidadeDTO> modalidades) {
        this.modalidades = modalidades;
    }

    public long getNumIdentificacaoCivil() {
        return numIdentificacaoCivil;
    }

    public void setNumIdentificacaoCivil(long numIdentificacaoCivil) {
        this.numIdentificacaoCivil = numIdentificacaoCivil;
    }

    public long getNumContribuinte() {
        return numContribuinte;
    }

    public void setNumContribuinte(long numContribuinte) {
        this.numContribuinte = numContribuinte;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }
}
