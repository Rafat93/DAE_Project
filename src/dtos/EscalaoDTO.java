package dtos;

import entities.Modalidade;

import java.io.Serializable;

public class EscalaoDTO implements Serializable {

    private String nome;
    private int idadeMin;
    private int idadeMax;
    private Modalidade modalidade;

    public EscalaoDTO() {
    }

    public EscalaoDTO(String nome, int idadeMin, int idadeMax, Modalidade modalidade) {
        this.nome = nome;
        this.idadeMin = idadeMin;
        this.idadeMax = idadeMax;
        this.modalidade = modalidade;
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

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }
}
