package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class ModalidadePraticada {
    @Id
    private int id;

    @NotNull
    private Modalidade modalidade;
    //Nullable
    private Escalao escalao;

    private Graduacao graduacao;
    @NotNull
    private Atleta atleta;
}
