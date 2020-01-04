package entities;

import javax.persistence.*;
import java.util.*;

@Entity
public class InscricaoAtletaModalidade{

    @Id
    private String code;

    @ManyToOne
    private Modalidade modalidade;

    @ManyToMany
    private Set<Treino> treinos;


    public InscricaoAtletaModalidade() {
    }

    public InscricaoAtletaModalidade(String code,Modalidade modalidade, Set<Treino> treinos) {
        this.code = code;
        this.modalidade = modalidade;
        this.treinos = treinos;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
