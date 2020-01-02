package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdmins",
                query = "SELECT a FROM Administrador a ORDER BY a.email" // JPQL
        )
})
public class Administrador extends User implements Serializable {

    public Administrador(String nome, String password, String email) {
        super(nome, password, email);
    }

    public Administrador() {
    }


}
