package dtos;

import java.io.Serializable;

public class AtletaDTO implements Serializable {
    private long numeroSocio;

    private String nome;

    private String email;

    private String password;

    private int idade;

    public AtletaDTO() {
    }

    public AtletaDTO(long numeroSocio,String nome, String email,String password, int idade) {
        this.numeroSocio = numeroSocio;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.idade = idade;
    }

    public long getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(long numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
