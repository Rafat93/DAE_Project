package entities;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAtletas",
                query = "SELECT a FROM Atleta a ORDER BY a.numeroSocio" // JPQL
        )
})
public class Atleta extends User implements Serializable {

    @NotNull
    @Column(unique=true)
    private int numeroSocio;

    @NotNull
    private int idade;

    public Atleta() {
        super();
    }

    public Atleta(int numeroSocio, String nome, String email,String password, int idade) {
        super(nome,password,email);
        this.numeroSocio = numeroSocio;
        this.idade = idade;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
