package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;


@Entity
@Table(name = "PRESENCAS")
@NamedQueries(
        @NamedQuery(
                name = "getAllPresencas",
                query = "SELECT p FROM Presenca p ORDER BY p.code"
        )
)
public class Presenca {

    @Id
    private String code;

    @NotNull
    @ManyToOne
    private Treino treino;

    @NotNull
    private GregorianCalendar dataTreino;

    @NotNull
    @ManyToMany
    private List<Atleta> atletasPresentes;

    @NotNull
    @ManyToOne
    private Treinador treinador;

    public Presenca() {
    }

    public Presenca(String code, Treino treino, GregorianCalendar dataTreino, Treinador treinador){
        this.code = code;
        this.treino = treino;
        this.dataTreino = dataTreino;
        this.treinador = treinador;
        this.atletasPresentes = new LinkedList<>();
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

    public List<Atleta> getAtletasPresentes() {
        return atletasPresentes;
    }

    public void setAtletasPresentes(List<Atleta> atletasPresentes) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
