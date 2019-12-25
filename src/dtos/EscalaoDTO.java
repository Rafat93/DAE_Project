package dtos;

import entities.Modalidade;

import java.io.Serializable;

public class EscalaoDTO implements Serializable {

    private String nome;
    private int idadeMin;
    private int idadeMax;
    private String siglaModalidade;

    public EscalaoDTO() {
    }

    public EscalaoDTO(String nome, int idadeMin, int idadeMax, String siglaModalidade) {
        this.nome = nome;
        this.idadeMin = idadeMin;
        this.idadeMax = idadeMax;
        this.siglaModalidade = siglaModalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdadeMin() {
        return idadeMin;
    }

    public void setIdadeMin(int idadeMin) {
        this.idadeMin = idadeMin;
    }

    public int getIdadeMax() {
        return idadeMax;
    }

    public void setIdadeMax(int idadeMax) {
        this.idadeMax = idadeMax;
    }

    public String getSiglaModalidade() {
        return siglaModalidade;
    }

    public void setSiglaModalidade(String siglaModalidade) {
        this.siglaModalidade = siglaModalidade;
    }
}
