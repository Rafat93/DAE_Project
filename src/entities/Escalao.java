package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Entity
public class Escalao {

    @Id
    @GeneratedValue
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
