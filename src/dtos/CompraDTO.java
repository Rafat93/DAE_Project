package dtos;

import enums.TipoPagamentoCompra;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;

public class CompraDTO {

    private String code;
    private String userEmail;
    private Collection <ProdutoDTO> produtos;
    private GregorianCalendar dataCompra;
    private TipoPagamentoCompra tipoPagamentoCompra;
    private Collection <PagamentoDTO> pagamentos;
    private double valorTotal;
    private double valorPago;

    public CompraDTO() {
        this.produtos = new LinkedList<>();
        this.pagamentos = new HashSet<>();
    }

    public CompraDTO(String code, String userEmail, GregorianCalendar dataCompra, TipoPagamentoCompra tipoPagamentoCompra, double valorTotal, double valorPago) {
        this.code = code;
        this.userEmail = userEmail;
        this.dataCompra = dataCompra;
        this.tipoPagamentoCompra = tipoPagamentoCompra;
        this.valorTotal = valorTotal;
        this.valorPago = valorPago;
        this.produtos = new LinkedList<>();
        this.pagamentos = new HashSet<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Collection<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(Collection<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    public GregorianCalendar getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(GregorianCalendar dataCompra) {
        this.dataCompra = dataCompra;
    }

    public TipoPagamentoCompra getTipoPagamentoCompra() {
        return tipoPagamentoCompra;
    }

    public void setTipoPagamentoCompra(TipoPagamentoCompra tipoPagamentoCompra) {
        this.tipoPagamentoCompra = tipoPagamentoCompra;
    }

    public Collection<PagamentoDTO> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(Collection<PagamentoDTO> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }
}
