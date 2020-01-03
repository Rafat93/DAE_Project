package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "GRADUACOES")
@NamedQueries({
        @NamedQuery(
                name = "getAllGraduacoes",
                query = "SELECT g FROM Graduacao g ORDER BY g.code" // JPQL
        )
})
public class Graduacao {

    @Id
    private String code;

    @NotNull
    private String nome;

    @ManyToOne
    @NotNull
    private Modalidade modalidade;

    public Graduacao() {
    }

    public Graduacao(String code, String nome, Modalidade modalidade){
        this.code = code;
        this.nome = nome;
        this.modalidade = modalidade;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }
}
