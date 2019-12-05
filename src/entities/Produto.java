package entities;

import com.sun.istack.internal.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Produto {

    @Id
    private int id;
    @NotNull
    private String tipo;
    @NotNull
    private String descricao;
    @NotNull
    private int preco;

    public Produto() {
    }
    public Produto(String tipo, String descricao, int preco) {
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

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }
}
