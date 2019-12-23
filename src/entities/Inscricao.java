package entities;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;

@Entity
@Table(name = "INSCRICOES")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Inscricao {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String nome;

    @NotNull
    private GregorianCalendar dataNascimento;

    @NotNull
    private long numIdentificacaoCivil;

    @NotNull
    private long numContibuinte;

    @NotNull
    private String morada;

    @NotNull
    @Email
    private String email;

    public Inscricao() {
    }

    public Inscricao(String nome,String email, int dia,int mes, int ano,long numIdentificacaoCivil, long numContibuinte, String morada) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = new GregorianCalendar(dia,mes,ano);
        this.numIdentificacaoCivil = numIdentificacaoCivil;
        this.numContibuinte = numContibuinte;
        this.morada = morada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getNumContibuinte() {
        return numContibuinte;
    }

    public void setNumContibuinte(long numContibuinte) {
        this.numContibuinte = numContibuinte;
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
}
