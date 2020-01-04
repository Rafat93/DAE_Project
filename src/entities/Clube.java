package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getClube",
                query = "SELECT a FROM Clube a ORDER BY a.nif" // JPQL
        )
})
public class Clube {

    @Id
    private long nif;
    @NotNull
    private String sigla;
    @NotNull
    private String nome;
    @NotNull
    private String email;

    private String descricao;
    @NotNull
    private String morada;
    @NotNull
    private long telefone;

    public Clube() {
    }

    public Clube(long nif,String sigla,String nome,String email, String descricao,String morada, long telefone) {
        this.nif=nif;
        this.sigla=sigla;
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
        this.morada = morada;
        this.telefone=telefone;
    }

    public long getNif() {
        return nif;
    }

    public void setNif(long nif) {
        this.nif = nif;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
