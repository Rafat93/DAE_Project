package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "MODALIDADES",
            uniqueConstraints = @UniqueConstraint(columnNames = {"NOME"}))
@NamedQueries({
        @NamedQuery(
                name = "getAllModalidades",
                query = "SELECT m FROM Modalidade m ORDER BY m.nome"
        )
})
public class Modalidade {

    @Id
    private String sigla;

    @NotNull
    private String nome;

    @NotNull
    @OneToOne
    private Horario horario;

    @NotNull
    @ManyToMany
    private Set<Escalao> escaloes;

    @OneToMany
    private List<Atleta> atletas;

     @OneToMany
    private List<Socio> socios;

}
