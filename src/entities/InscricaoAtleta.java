package entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.*;

@Entity
public class InscricaoAtleta extends Inscricao{

    @ManyToMany
    private Set<Modalidade> modalidades;

    @ManyToMany
    private Set<Treino> treinos;


    public InscricaoAtleta() {
    }

    public InscricaoAtleta(String code, String nome, String email, GregorianCalendar dataNascimento, long numIdentificacaoCivil, long numContibuinte, String morada, Set<Modalidade> modalidades, Set<Treino> treinos) {
        super(code, nome, email, dataNascimento, numIdentificacaoCivil, numContibuinte, morada);
        this.modalidades = modalidades;
        this.treinos = treinos;
    }

    public Set<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public Set<Treino> getTreinos() {
        return treinos;
    }

    public void setTreinos(Set<Treino> treinos) {
        this.treinos = treinos;
    }

}
