package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Recibo {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String entidade;

    @NotNull
    private long nifEntidade;

    public Recibo() {
    }
}
