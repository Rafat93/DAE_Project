package entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
public class Treinador extends User implements Serializable {

    @NotNull
    private long numeroCedula;

    @OneToMany
    private List<Treino> treinosLecionados;

    public Treinador(String nome, String password, String email,long numeroCedula) {
        super(nome, password, email);
        this.numeroCedula=numeroCedula;
    }

    public Treinador() {
    }


}
