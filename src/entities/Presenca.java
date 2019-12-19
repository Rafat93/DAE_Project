package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;
import java.util.Set;

@Entity
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

    public Presenca(Treino treino, Data dataTreino, Treinador treinador){

    }
}
