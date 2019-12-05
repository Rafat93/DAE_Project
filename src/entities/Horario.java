package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
public class Horario {
    @Id
    private int id;

    @OneToMany(mappedBy = "horario", cascade = CascadeType.REMOVE)
    private List<Treino> treinos;

    @OneToOne
    private Modalidade modalidade;

}
