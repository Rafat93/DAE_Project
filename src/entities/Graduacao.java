package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Graduacao {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String nome;

    @ManyToOne
    @NotNull
    private Modalidade modalidade;

    public Graduacao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
