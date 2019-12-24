package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllTreinadores",
                query = "SELECT t FROM Treinador t ORDER BY t.nome" // JPQL
        )
})
public class Treinador extends User implements Serializable {

    @NotNull
    private long numeroCedula;

    @OneToMany
    private List<Treino> treinosLecionados;

    public Treinador(String nome, String password, String email,long numeroCedula) {
        super(nome, password, email);
        this.numeroCedula=numeroCedula;
        this.treinosLecionados = new LinkedList<>();
    }

    public Treinador() {
    }

    public long getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(long numeroCedula) {
        this.numeroCedula = numeroCedula;
    }

    public List<Treino> getTreinosLecionados() {
        return treinosLecionados;
    }

    public void setTreinosLecionados(List<Treino> treinosLecionados) {
        this.treinosLecionados = treinosLecionados;
    }

    public void addTreinoLecionado (Treino treino){
        if (!treinosLecionados.contains(treino)){
            treinosLecionados.add(treino);
        }
    }

    public void removeTreinoLecionado (Treino treino){
        if (treinosLecionados.contains(treino)){
            treinosLecionados.remove(treino);
        }
    }
}
