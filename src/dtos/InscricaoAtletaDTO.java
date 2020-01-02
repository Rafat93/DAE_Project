package dtos;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class InscricaoAtletaDTO {

    private String code;
    private String nome;
    private GregorianCalendar dataNascimento;
    private long numIdentificacaoCivil;
    private long numContribuinte;
    private String morada;
    private String email;
    private boolean confirmed;
    private Collection <ModalidadeDTO> modalidades;
    private Collection <TreinoDTO> treinos;

    public InscricaoAtletaDTO() {
        treinos = new LinkedList<>();
        modalidades = new LinkedList<>();
    }

    public InscricaoAtletaDTO (String code, String nome, GregorianCalendar dataNascimento, long numIdentificacaoCivil, long numContribuinte, String morada, String email) {
        this();
        this.code = code;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
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

    public GregorianCalendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(GregorianCalendar dataNascimento) {
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
