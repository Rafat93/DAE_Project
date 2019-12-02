package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAtletas",
                query = "SELECT a FROM Atleta a ORDER BY a.numeroSocio" // JPQL
        )
})
public class Atleta implements Serializable {
    @Id
    private int numeroSocio;

    private String nome;

    private String email;

    private int idade;

    public Atleta() {
    }

    public Atleta(int numeroSocio, String nome, String email, int idade) {
        this.numeroSocio = numeroSocio;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
