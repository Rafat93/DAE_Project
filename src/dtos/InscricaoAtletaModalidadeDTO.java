package dtos;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import static utils.Utilitarios.format;

public class InscricaoAtletaModalidadeDTO {

    private String code;
    private String nome;
    private String dataNascimento;
    private long numIdentificacaoCivil;
    private long numContribuinte;
    private String morada;
    private String email;
    private boolean confirmed;
    private Collection <ModalidadeDTO> modalidades;
    private Collection <TreinoDTO> treinos;

    public InscricaoAtletaModalidadeDTO() {
        treinos = new LinkedList<>();
        modalidades = new LinkedList<>();
    }

    public InscricaoAtletaModalidadeDTO(String code, String nome, GregorianCalendar dataNascimento, long numIdentificacaoCivil, long numContribuinte, String morada, String email) {
        this();
        this.code = code;
        this.nome = nome;
        this.dataNascimento = format(dataNascimento);
        this.numIdentificacaoCivil = numIdentificacaoCivil;
        this.numContribuinte = numContribuinte;
        this.morada = morada;
        this.email = email;
        this.confirmed = false;
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

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public long getNumIdentificacaoCivil() {
        return numIdentificacaoCivil;
    }

    public void setNumIdentificacaoCivil(long numIdentificacaoCivil) {
        this.numIdentificacaoCivil = numIdentificacaoCivil;
    }

    public long getNumContribuinte() {
        return numContribuinte;
    }

    public void setNumContribuinte(long numContribuinte) {
        this.numContribuinte = numContribuinte;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Collection<ModalidadeDTO> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Collection<ModalidadeDTO> modalidades) {
        this.modalidades = modalidades;
    }

    public Collection<TreinoDTO> getTreinos() {
        return treinos;
    }

    public void setTreinos(Collection<TreinoDTO> treinos) {
        this.treinos = treinos;
    }
}
