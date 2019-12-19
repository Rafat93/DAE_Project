package dtos;

import entities.Modalidade;

import java.io.Serializable;

public class GraduacaoDTO implements Serializable {

    private String name;
    private Modalidade modalidade;

    public GraduacaoDTO(String name, Modalidade modalidade) {
        this.name = name;
        this.modalidade = modalidade;
    }

    public GraduacaoDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }
}
