package dtos;

public class TipoProdutoDTO {

    private String nome;

    public TipoProdutoDTO(String nome) {
        this.nome = nome;
    }

    public TipoProdutoDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
