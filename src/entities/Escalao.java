package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "ESCALOES",
        uniqueConstraints = @UniqueConstraint(columnNames = {"NOME"}))
@NamedQueries({
        @NamedQuery(
                name = "getAllEscaloes",
                query = "SELECT e FROM Escalao e ORDER BY e.code" // JPQL
        )
})
public class Escalao {

    @Id
    private String code;

    @NotNull
    private String nome;

    @NotNull
    private int idadeMin;

    @NotNull
    private int idadeMax;

    @NotNull
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Modalidade modalidade;

    public Escalao() {
    }

    public Escalao(String code, String nome, int idadeMin, int idadeMax, Modalidade modalidade) {
        this.code=code;
        this.nome = nome;
        this.idadeMin = idadeMin;
        this.idadeMax = idadeMax;
        this.modalidade = modalidade ;
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

    public int getIdadeMin() {
        return idadeMin;
    }

    public void setIdadeMin(int idadeMin) {
        this.idadeMin = idadeMin;
    }

    public int getIdadeMax() {
        return idadeMax;
    }

    public void setIdadeMax(int idadeMax) {
        this.idadeMax = idadeMax;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }
}
