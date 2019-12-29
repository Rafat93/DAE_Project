package dtos;

import entities.Modalidade;

import java.io.Serializable;

public class GraduacaoDTO implements Serializable {

    private String name;
    private String siglaModalidade;

    public GraduacaoDTO(String name, String siglaModalidade) {
        this.name = name;
        this.siglaModalidade = siglaModalidade;
    }

    public GraduacaoDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiglaModalidade() {
        return siglaModalidade;
    }

    public void setSiglaModalidade(String siglaModalidade) {
        this.siglaModalidade = siglaModalidade;
    }
}
