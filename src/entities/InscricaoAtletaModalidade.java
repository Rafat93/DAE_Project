package entities;

import javax.persistence.*;
import java.util.*;

@Entity
public class InscricaoAtletaModalidade{

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Modalidade modalidade;

    @ManyToMany
    private Set<Treino> treinos;


    public InscricaoAtletaModalidade() {
    }

    public InscricaoAtletaModalidade(Modalidade modalidade, Set<Treino> treinos) {
        this.modalidade = modalidade;
        this.treinos = treinos;
    }

    public long getId() {
        return id;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public Set<Treino> getTreinos() {
        return treinos;
    }

    public void setTreinos(Set<Treino> treinos) {
        this.treinos = treinos;
    }

}
