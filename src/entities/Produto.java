package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PRODUTOS", uniqueConstraints = @UniqueConstraint(columnNames = {"TIPO"}))
@NamedQueries({
        @NamedQuery(
                name="getAllProdutos",
                query = "SELECT p FROM Produto p ORDER BY p.tipo"
        )
})
public class Produto {

    @Id
    private int id;
    @NotNull
    private String tipo;
    @NotNull
    private String descricao;
    @NotNull
    private double preco;

    public Produto() {
    }

    public Produto(String tipo, String descricao, double preco) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
