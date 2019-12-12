package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Entity
public class Escalao {

    @Id
    private int id;

    @NotNull
    private String nome;

    @NotNull
    private int idadeMin;

    @NotNull
    private int idadeMax;

    @NotNull
    @ManyToOne
    private Modalidade modalidade;

    public Escalao() {
    }
    public Escalao(String nome, int idadeMin, int idadeMax, Modalidade modalidade) {
        this.nome = nome;
        this.idadeMin = idadeMin;
        this.idadeMax = idadeMax;
        this.modalidade = modalidade ;
    }
}
