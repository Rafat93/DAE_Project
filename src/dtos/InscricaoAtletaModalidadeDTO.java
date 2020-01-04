package dtos;


import java.util.LinkedList;
import java.util.List;


public class InscricaoAtletaModalidadeDTO {

    private String code;
    private String email;
    private String siglaModalidade;
    private List<String> treinos;

    public InscricaoAtletaModalidadeDTO() {
        treinos = new LinkedList<>();
    }

    public InscricaoAtletaModalidadeDTO(String code,String email, String siglaModalidade) {
        this();
        this.code = code;
        this.email=email;
        this.siglaModalidade = siglaModalidade;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSiglaModalidade() {
        return siglaModalidade;
    }

    public void setSiglaModalidade(String siglaModalidade) {
        this.siglaModalidade = siglaModalidade;
    }

    public List <String> getTreinos() {
        return treinos;
    }

    public void setTreinos(List <String> treinos) {
        this.treinos = treinos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
