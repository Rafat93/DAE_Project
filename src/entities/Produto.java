package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PRODUTOS", uniqueConstraints = @UniqueConstraint(columnNames = {"TIPO"}))
@NamedQueries({
        @NamedQuery(
                name="getAllProdutos",
                query = "SELECT p FROM Produto p ORDER BY p.id"
        )
})
public class Produto {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @ManyToOne
    private TipoProduto tipo;

    @NotNull
    private String descricao;

    @NotNull
    private double precoEmEuros;

    @NotNull
    private int stock;

    public Produto() {
    }

    public Produto(TipoProduto tipo, String descricao, double preco, int stock) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.precoEmEuros = preco;
        this.stock = stock;
    }

    public TipoProduto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoEmEuros() {
        return precoEmEuros;
    }

    public void setPrecoEmEuros(double precoEmEuros) {

        this.precoEmEuros = precoEmEuros;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
