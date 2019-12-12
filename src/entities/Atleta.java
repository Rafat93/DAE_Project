package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAtletas",
                query = "SELECT a FROM Atleta a ORDER BY a.numeroSocio" // JPQL
        )
})
public class Atleta extends Socio implements Serializable {

    @NotNull
    private Date dataNascimento;

    @OneToMany
    private Set<Inscricao> inscricoes;


    public Atleta() {
        super();
    }

    public Atleta(int numeroSocio, String nome, String email,String password, Date dataNascimento) {
        super(numeroSocio,nome,password,email);
        this.dataNascimento = dataNascimento;
        this.inscricoes = new LinkedHashSet<>();
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Set<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(Set<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }
}
