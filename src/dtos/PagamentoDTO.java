package dtos;

import entities.Recibo;
import enums.EstadoPagamento;
import entities.Produto;
import entities.User;

import java.io.Serializable;
import java.util.Date;

public class PagamentoDTO  implements Serializable {

    private int id;
    private User utilizador;
    private Produto produto;
    private Date dataLancamento;
    private int quantidade;
    private double precoFinalEmEuros;
    private EstadoPagamento estado;
    private Recibo recibo;

    public PagamentoDTO() {
    }

    public PagamentoDTO(User utilizador, Produto produto, Date dataLancamento, int quantidade, double precoFinal, EstadoPagamento estado, Recibo recibo) {
        this.utilizador = utilizador;
        this.produto = produto;
        this.dataLancamento = dataLancamento;
        this.quantidade = quantidade;
        this.precoFinalEmEuros = precoFinal;
        this.estado = estado;
        this.recibo = recibo;
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

    public double getPrecoFinalEmEuros() {
        return precoFinalEmEuros;
    }

    public void setPrecoFinalEmEuros(double precoFinalEmEuros) {
        this.precoFinalEmEuros = precoFinalEmEuros;
    }

    public EstadoPagamento getEstado() {
        return estado;
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }
}
