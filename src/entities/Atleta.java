package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.GregorianCalendar;
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
    private GregorianCalendar dataNascimento;

    @OneToMany
    private Set<Inscricao> inscricoes;


    public Atleta() {
        super();
    }

    public Atleta(long numeroSocio, String nome, String email,String password, GregorianCalendar dataNascimento) {
        super(numeroSocio,nome,password,email);
        this.dataNascimento = dataNascimento;
        this.inscricoes = new LinkedHashSet<>();
    }

    public GregorianCalendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(GregorianCalendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Set<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(Set<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }

    public void addInscricao (Inscricao inscricao){
        if (!inscricoes.contains(inscricao)){
            inscricoes.add(inscricao);
        }
    }

    public void removeInscricao(Inscricao inscricao){
        if (inscricoes.contains(inscricao)){
            inscricoes.remove(inscricao);
        }
    }
}
