package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "PRESENCAS")
@NamedQueries(
        @NamedQuery(
                name = "getAllPresencas",
                query = "SELECT p FROM Presenca p ORDER BY p.id"
        )
)
public class Presenca {

    @Id
    private int id;

    @NotNull
    @ManyToOne
    private Treino treino;

    @NotNull
    private GregorianCalendar dataTreino;

    @NotNull
    @ManyToMany
    private Set<Atleta> atletasPresentes;

    @NotNull
    @ManyToOne
    private Treinador treinador;

    public Presenca() {
    }

    public Presenca(Treino treino, GregorianCalendar dataTreino, Treinador treinador){
        this.treino = treino;
        this.dataTreino = dataTreino;
        this.treinador = treinador;
        this.atletasPresentes = new HashSet<>();
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public GregorianCalendar getDataTreino() {
        return dataTreino;
    }

    public void setDataTreino(GregorianCalendar dataTreino) {
        this.dataTreino = dataTreino;
    }

    public Set<Atleta> getAtletasPresentes() {
        return atletasPresentes;
    }

    public void setAtletasPresentes(Set<Atleta> atletasPresentes) {
        this.atletasPresentes = atletasPresentes;
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }

    public void addAtletaPresente (Atleta atleta){
        if (!atletasPresentes.contains(atleta)){
            atletasPresentes.add(atleta);
        }
    }

    public void removeAtletaPresente (Atleta atleta){
        if (atletasPresentes.contains(atleta)){
            atletasPresentes.remove(atleta);
        }
    }
}
