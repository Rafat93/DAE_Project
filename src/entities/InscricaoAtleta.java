package entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

@Entity
public class InscricaoAtleta extends Inscricao{

    @ManyToMany
    private Set<Modalidade> modalidades;

    @ManyToMany
    private List<Treino> treinos;


    public InscricaoAtleta() {
    }

    public InscricaoAtleta(String nome, String email, int dia, int mes, int ano, long numIdentificacaoCivil, long numContibuinte, String morada,Set<Modalidade> modalidades, List<Treino> treinos) {
        super(nome, email, dia, mes, ano, numIdentificacaoCivil, numContibuinte, morada);
        this.modalidades = modalidades;
        this.treinos = treinos;
    }

    public Set<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public List<Treino> getTreinos() {
        return treinos;
    }

    public void setTreinos(List<Treino> treinos) {
        this.treinos = treinos;
    }

}
