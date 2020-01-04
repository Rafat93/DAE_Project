package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllCarrinhos",
                query = "SELECT a FROM Carrinho a ORDER BY a.code" // JPQL
        )
})
public class Carrinho {

    @Id
    private String code;
    @NotNull
    private String email;

    @NotNull
    @ManyToMany
    private List<Produto> produtos;

    public Carrinho() {
        this.produtos = new LinkedList<>();
    };

    public Carrinho(String code, String email) {
        this();
        this.code = code;
        this.email = email;
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void addProduto(Produto produto){

    }
}


