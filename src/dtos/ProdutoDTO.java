package dtos;

import entities.TipoProduto;

import javax.persistence.Id;
import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    private String code;
    private TipoProduto tipo;
    private String descricao;
    private double preco;
    private int stock;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String code,TipoProduto tipo, String descricao, double preco,int stock) {
        this.code = code;
        this.tipo = tipo;
        this.descricao = descricao;
        this.preco = preco;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
