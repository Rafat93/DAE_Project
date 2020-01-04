package dtos;

import entities.Produto;

import java.util.List;

public class CarrinhoDTO {

    private String code;

    private String email;

    private List<Produto> produtos;

    public CarrinhoDTO(String code, String email, List<Produto> produtos) {
        this.code = code;
        this.email = email;
        this.produtos = produtos;
    }
}
