package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
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

    //Falta colocar a epoca desportiva

    @Id
    private String sigla;

    @NotNull
    private String nome;

    @NotNull
    @OneToMany
    private List<Treino> treinos;

    @NotNull
    @ManyToMany
    private Set<Escalao> escaloes;

    @OneToMany
    private List<Atleta> atletas;

    @ManyToMany
    private List<Socio> socios;

    @Override
    public boolean equals(Object o) {
        return o instanceof Modalidade && ((Modalidade) o).nome.equals(this.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sigla, nome, treinos, escaloes, atletas, socios);
    }
}
