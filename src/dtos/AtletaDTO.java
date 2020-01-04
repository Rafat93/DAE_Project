package dtos;

import java.io.Serializable;
import java.util.*;
import static utils.Utilitarios.*;

public class AtletaDTO implements Serializable {
    private long numeroSocio;

    private String nome;

    private String email;

    private String password;

    private String dataNascimento;

    private Collection<ModalidadeDTO> modalidades;

    private long numIdentificacaoCivil;

    private long numContribuinte;

    private String morada;

    public AtletaDTO() {
        modalidades = new LinkedList<>();
    }

    public AtletaDTO(long numeroSocio,String nome, String email,String password, GregorianCalendar dataNascimento,long numIdentificacaoCivil, long numContribuinte, String morada) {
        this();
        this.numeroSocio = numeroSocio;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.dataNascimento = format(dataNascimento);
        this.morada = morada;
        this.numContribuinte = numContribuinte;
        this.numIdentificacaoCivil = numIdentificacaoCivil;
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

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<ModalidadeDTO> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Collection<ModalidadeDTO> modalidades) {
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
