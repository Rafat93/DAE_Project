package dtos;

import entities.Treino;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class TreinadorDTO implements Serializable {
    private long numeroCedula;

    private String nome;

    private String email;

    private String password;

    private List<Treino> treinosLecionados;

    private List<ModalidadeDTO> modalidades;

    public TreinadorDTO(long numeroCedula, String nome, String email, String password) {
        this.numeroCedula = numeroCedula;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.treinosLecionados = new LinkedList<>();
        this.modalidades = new LinkedList<>();
    }

    public TreinadorDTO() {
        this.treinosLecionados = new LinkedList<>();
        this.modalidades = new LinkedList<>();
    }

    public long getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(long numeroCedula) {
        this.numeroCedula = numeroCedula;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Treino> getTreinosLecionados() {
        return treinosLecionados;
    }

    public void setTreinosLecionados(List<Treino> treinosLecionados) {
        this.treinosLecionados = treinosLecionados;
    }

    public List<ModalidadeDTO> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<ModalidadeDTO> modalidades) {
        this.modalidades = modalidades;
    }
}
