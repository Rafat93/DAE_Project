package entities;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;

@Entity
@Table(name = "INSCRICOES")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "getAllInscricoes",
                query = "SELECT i FROM Inscricao i ORDER BY i.nome" // JPQL
        ),
        @NamedQuery(
                name = "getInscricoesNotConfirmed",
                query = "SELECT i FROM Inscricao i WHERE i.confirmed LIKE 'FALSE' ORDER BY i.nome " // JPQL
        )
})
public class Inscricao {

    @Id
    private String code;

    @NotNull
    private String nome;

    @NotNull
    private GregorianCalendar dataNascimento;

    @NotNull
    private long numIdentificacaoCivil;

    @NotNull
    private long numContribuinte;

    @NotNull
    private String morada;

    @NotNull
    @Email
    private String email;

    private boolean confirmed;

    public Inscricao() {
    }

    public Inscricao(String code, String nome,String email, GregorianCalendar dataNascimento,long numIdentificacaoCivil, long numContibuinte, String morada) {
        this.code = code;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.numIdentificacaoCivil = numIdentificacaoCivil;
        this.numContribuinte = numContibuinte;
        this.morada = morada;
        this.confirmed = false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
