package dtos;

import java.io.Serializable;

public class GraduacaoDTO implements Serializable {

    private String code;
    private String nome;
    private String siglaModalidade;

    public GraduacaoDTO(String code, String nome, String siglaModalidade) {
        this.code = code;
        this.nome = nome;
        this.siglaModalidade = siglaModalidade;
    }

    public GraduacaoDTO() {
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

    public String getSiglaModalidade() {
        return siglaModalidade;
    }

    public void setSiglaModalidade(String siglaModalidade) {
        this.siglaModalidade = siglaModalidade;
    }
}
