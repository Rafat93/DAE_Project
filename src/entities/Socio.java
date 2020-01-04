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
                name = "getAllSocios",
                query = "SELECT s FROM Socio s ORDER BY s.numeroSocio" // JPQL
        )
})
public class Socio extends User implements Serializable {

    @NotNull
    @Column(unique=true)
    private long numeroSocio;

    @NotNull
    private GregorianCalendar dataNascimento;

    @NotNull
    private long numIdentificacaoCivil;

    @NotNull
    private long numContribuinte;

    @NotNull
    private String morada;
    //

    public Socio() {

    }

    public Socio(long numeroSocio, String nome, String password, String email, GregorianCalendar dataNascimento, long numIdentificacaoCivil, long numContribuinte, String morada) {
        super(nome, password, email);
        this.numeroSocio = numeroSocio;
        this.dataNascimento = dataNascimento;
        this.morada = morada;
        this.numContribuinte = numContribuinte;
        this.numIdentificacaoCivil = numIdentificacaoCivil;
    }

    public long getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(long numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public GregorianCalendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(GregorianCalendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public long getNumIdentificacaoCivil() {
        return numIdentificacaoCivil;
    }

    public void setNumIdentificacaoCivil(long numIdentificacaoCivil) {
        this.numIdentificacaoCivil = numIdentificacaoCivil;
    }

    public long getNumContribuinte() {
        return numContribuinte;
    }

    public void setNumContribuinte(long numContibuinte) {
        this.numContribuinte = numContibuinte;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }
}
