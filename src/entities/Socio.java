package entities;

import com.sun.istack.internal.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSocios",
                query = "SELECT s FROM Socio s ORDER BY s.numeroSocio" // JPQL
        )
})
public class Socio extends User implements Serializable {

    @NotNull
    @Column(unique=true)
    private long numeroSocio;

    public Socio() {
    }

    public Socio(int numeroSocio, String nome, String password, String email) {
        super(nome, password, email);
        this.numeroSocio = numeroSocio;
    }

    public long getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(long numeroSocio) {
        this.numeroSocio = numeroSocio;
    }
}
