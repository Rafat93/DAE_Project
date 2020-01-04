package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class InscricaoAtletaModalidade extends Inscricao{

    @ManyToOne
    @NotNull
    private Modalidade modalidade;

    @ManyToMany
    private Set<Treino> treinos;

    private boolean confirmed;


    public InscricaoAtletaModalidade() {
    }

    public InscricaoAtletaModalidade(String code, String nome,String email, GregorianCalendar dataNascimento,long numIdentificacaoCivil, long numContibuinte, String morada,Modalidade modalidade, Set<Treino> treinos) {
        super(code,nome,email,dataNascimento,numIdentificacaoCivil,numContibuinte,morada);
        this.modalidade = modalidade;
        this.treinos = treinos;
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
