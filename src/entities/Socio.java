package entities;

import com.sun.istack.internal.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Socio extends User implements Serializable {

    @NotNull
    @Column(unique=true)
    private int numeroSocio;

    public Socio() {
    }

    public Socio(int numeroSocio, String nome, String password, String email) {
        super(nome, password, email);
        this.numeroSocio = numeroSocio;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }
}
