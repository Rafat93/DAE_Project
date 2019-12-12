package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Inscricao {

    @Id
    private int id;

    @NotNull
    @ManyToOne
    private Modalidade modalidade;

    @NotNull
    @ManyToOne
    private Escalao escalao;

    @ManyToOne
    private Graduacao graduacao;

    @NotNull
    @ManyToOne
    private Atleta atleta;

    @NotNull
    @ManyToOne
    private Horario horario;

    public Inscricao() {
    }


}
