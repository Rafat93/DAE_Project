package entities;

import enums.EstadoPagamento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "PAGAMENTOS")
@NamedQueries(
        @NamedQuery(
                name = "getAllPagamentos",
                query = "SELECT p FROM Pagamento p ORDER BY p.dataLancamento"
        )
)
public class Pagamento {
    @Id
    private int id;

    @NotNull
    @OneToOne
    private User utilizador;

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    private Date dataLancamento;

    @NotNull
    private int quantidade;

    @NotNull
    private double precoFinalEmEuros;

    @NotNull
    private EstadoPagamento estado;

    @OneToOne
    private Recibo recibo;



    public Pagamento() {
    }

    public Pagamento(User user, Produto produto, Date dataLancamento, int quantidade, double precoFinal, EstadoPagamento estado) {
        this.utilizador = user;
        this.produto = produto;
        this.dataLancamento = dataLancamento;
        this.quantidade = quantidade;
        this.precoFinalEmEuros = precoFinal;
        this.estado = estado;
    }
    public Pagamento(User user, Produto produto, Date dataLancamento, int quantidade, double precoFinal, EstadoPagamento estado, Recibo recibo) {
        this.utilizador = user;
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

    public void setPrecoFinalEmEuros(double precoFinal) {
        this.precoFinalEmEuros = precoFinal;
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
