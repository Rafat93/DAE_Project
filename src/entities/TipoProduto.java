package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllTipoProdutos",
                query = "SELECT t FROM TipoProduto t ORDER BY t.nome" // JPQL
        )
})
public class TipoProduto  {

    @Id
    private String nome;

    public TipoProduto() {
    }

    public TipoProduto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
