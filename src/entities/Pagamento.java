package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "PAGAMENTOS")
@Entity
public class Pagamento {
    @Id
    private int id;

    @NotNull
    @OneToMany
    private User utilizador;

    @NotNull
    @OneToMany
    private Produto produto;

    @NotNull
    private Date dataLancamento;

    @NotNull
    private int quantidade;

    @NotNull
    private int precoFinal;

    @NotNull
    private EnumEstado estado;



    public Pagamento() {
    }

    public Pagamento(User user, Produto produto, Date dataLancamento, int quantidade, int precoFinal, EnumEstado estado) {
        this.utilizador = user;
        this.produto = produto;
        this.dataLancamento = dataLancamento;
        this.quantidade = quantidade;
        this.precoFinal = precoFinal;
        this.estado = estado;
    }

}
