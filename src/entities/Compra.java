package entities;


import enums.TipoPagamentoCompra;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name="COMPRAS")
@NamedQueries({
        @NamedQuery(
                name = "getAllCompras",
                query = "SELECT c FROM Compra c ORDER BY c.code" // JPQL
        )
})
public class Compra {

    @Id
    private String code;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToMany
    private List<Produto> produtos;

    @NotNull
    private GregorianCalendar dataCompra;

    @NotNull
    private TipoPagamentoCompra tipoPagamentoCompra;

    @NotNull
    @ManyToMany
    private Set<Pagamento> pagamentos;

    @NotNull
    private double valorTotal;

    @NotNull
    private double valorPago;


    public Compra() {
        this.produtos = new LinkedList<>();
        this.pagamentos = new HashSet<>();
    }

    public Compra(String code, User user, GregorianCalendar dataCompra, TipoPagamentoCompra tipoPagamentoCompra, double valorTotal, double valorPago) {
        this.code = code;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
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

    public Set<Pagamento> getPagamentos() {
        return pagamentos;
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

    public void addProduto (Produto produto){
        if (!produtos.contains(produto)){
            produtos.add(produto);
        }
    }

    public void addPagamento (Pagamento pagamento){
        if (!pagamentos.contains(pagamento)){
            pagamentos.add(pagamento);
        }
    }

    public void removeProduto (Produto produto){
        produtos.remove(produto);
    }

    public void removePagamento (Pagamento pagamento){
        pagamentos.remove(pagamento);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Compra && ((Compra) o).code.equals(this.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, user, produtos, dataCompra, tipoPagamentoCompra, pagamentos, valorTotal, valorPago);
    }
}
