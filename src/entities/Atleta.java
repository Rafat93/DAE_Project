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
public class Atleta extends Socio implements Serializable {

    @NotNull
    private int idade;


    public Atleta() {
        super();
    }

    public Atleta(int numeroSocio, String nome, String email,String password, int idade) {
        super(numeroSocio,nome,password,email);
        this.idade = idade;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
