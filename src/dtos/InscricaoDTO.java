package dtos;

import java.util.GregorianCalendar;

import static utils.Utilitarios.format;

public class InscricaoDTO {

    private String code;
    private String nome;
    private String dataNascimento;
    private long numIdentificacaoCivil;
    private long numContribuinte;
    private String morada;
    private String email;
    private boolean confirmed;

    public InscricaoDTO() {
    }

    public InscricaoDTO(String code, String nome, GregorianCalendar dataNascimento, long numIdentificacaoCivil, long numContribuinte, String morada, String email) {
        this.code = code;
        this.nome = nome;
        this.dataNascimento = format(dataNascimento);
        this.numIdentificacaoCivil = numIdentificacaoCivil;
        this.numContribuinte = numContribuinte;
        this.morada = morada;
        this.email = email;
        this.confirmed = false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
