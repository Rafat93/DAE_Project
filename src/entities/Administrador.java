package entities;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Administrador extends User implements Serializable {

    public Administrador(String nome, String password, String email) {
        super(nome, password, email);
    }

    public Administrador() {
    }
}
