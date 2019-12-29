package entities;

import enums.EstadoPagamento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;
import java.util.GregorianCalendar;


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
    private String code;

    @NotNull
    @OneToOne
    private User utilizador;

    @NotNull
    @ManyToOne
    private Compra compra;

    @NotNull
    private GregorianCalendar dataLancamento;

    private GregorianCalendar dataPagamento;

    private GregorianCalendar dataLimitePagamento;

    @NotNull
    private double precoFinalEmEuros;

    @NotNull
    private EstadoPagamento estado;

    @OneToOne
    private Recibo recibo;



    public Pagamento() {
    }

    public Pagamento(String code,User user, Compra compra,GregorianCalendar dataLancamento, GregorianCalendar dataLimitePagamento, double precoFinal, EstadoPagamento estado) {
        this.code = code;
        this.utilizador = user;
        this.compra = compra;
        this.dataLancamento = dataLancamento;
        this.dataLimitePagamento = dataLimitePagamento;
        this.precoFinalEmEuros = precoFinal;
        this.estado = estado;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(User utilizador) {
        this.utilizador = utilizador;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public GregorianCalendar getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(GregorianCalendar dataLancamento) {
        this.dataLancamento = dataLancamento;
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
