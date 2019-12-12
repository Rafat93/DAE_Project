package entities;

import enums.EstadoPagamento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


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
    private double precoFinal;

    @NotNull
    private EstadoPagamento estado;



    public Pagamento() {
    }

    public Pagamento(User user, Produto produto, Date dataLancamento, int quantidade, double precoFinal, EstadoPagamento estado) {
        this.utilizador = user;
        this.produto = produto;
        this.dataLancamento = dataLancamento;
        this.quantidade = quantidade;
        this.precoFinal = precoFinal;
        this.estado = estado;
    }

}
