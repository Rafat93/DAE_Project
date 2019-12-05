package dtos;

import entities.EnumEstado;
import entities.Pagamento;
import entities.Produto;
import entities.User;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class PagamentoDTO  implements Serializable {

    private int id;
    private User utilizador;
    private Produto produto;
    private Date dataLancamento;
    private int quantidade;
    private double precoFinal;
    private EnumEstado estado;

    public PagamentoDTO() {
    }

    public PagamentoDTO(User utilizador, Produto produto, Date dataLancamento, int quantidade, double precoFinal, EnumEstado estado) {
        this.utilizador = utilizador;
        this.produto = produto;
        this.dataLancamento = dataLancamento;
        this.quantidade = quantidade;
        this.precoFinal = precoFinal;
        this.estado = estado;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(User utilizador) {
        this.utilizador = utilizador;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public EnumEstado getEstado() {
        return estado;
    }

    public void setEstado(EnumEstado estado) {
        this.estado = estado;
    }
}
