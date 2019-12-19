package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="RECIBOS")
@NamedQueries(
        @NamedQuery(
                name = "getAllRecibos",
                query = "SELECT r FROM Recibo r order by r.id"
        )
)
public class Recibo {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String entidade;

    @NotNull
    private long nifEntidade;

    @NotNull
    @OneToOne
    private Pagamento pagamento;

    public Recibo() {

    }

    public Recibo(Pagamento pagamento){
        this.entidade = "Clube";
        this.nifEntidade= 912456325;
        this.pagamento = pagamento;
    }
}
