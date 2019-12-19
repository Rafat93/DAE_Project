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
    private Pagamento Pagamento;

    public Recibo() {

    }

    public Recibo(Pagamento Pagamento){
        this.entidade = "Clube";
        this.nifEntidade= 912456325;

    }
}
