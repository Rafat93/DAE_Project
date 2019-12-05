package dtos;

import com.sun.istack.internal.NotNull;

import javax.persistence.Id;
import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    private int id;
    private String tipo;
    private String descricao;
    private double preco;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String tipo, String descricao, double preco) {
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
