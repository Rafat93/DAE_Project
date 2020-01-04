package dtos;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class ClubeDTO {


    private long nif;
    private String sigla;
    private String nome;
    private String email;
    private String descricao;
    private String morada;
    private long telefone;

    public ClubeDTO(long nif,String sigla, String nome, String email, String descricao, String morada, long telefone) {
        this.nif = nif;
        this.sigla = sigla;
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
        this.morada = morada;
        this.telefone = telefone;
    }

    public ClubeDTO() {
    }

    public long getNif() {
        return nif;
    }

    public void setNif(long nif) {
        this.nif = nif;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
