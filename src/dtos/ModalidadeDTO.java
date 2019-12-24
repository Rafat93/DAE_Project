package dtos;

import entities.Atleta;
import entities.Escalao;
import entities.Socio;
import entities.Treino;

import java.io.Serializable;
import java.util.*;

public class ModalidadeDTO implements Serializable {

    private String sigla;
    private String nome;
    private String epocaDesportiva;
    private Collection<TreinoDTO> treinos;
    private Collection<EscalaoDTO> escaloes;
    private Collection<AtletaDTO> atletas;
    private Collection<SocioDTO> socios;

    public ModalidadeDTO(String sigla, String nome, String epocaDesportiva) {
        this();
        this.sigla = sigla;
        this.nome = nome;
        this.epocaDesportiva = epocaDesportiva;
    }

    public ModalidadeDTO() {
        treinos = new LinkedList<>();
        escaloes = new LinkedList<>();
        atletas = new LinkedList<>();
        socios = new LinkedList<>();

    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEpocaDesportiva() {
        return epocaDesportiva;
    }

    public void setEpocaDesportiva(String epocaDesportiva) {
        this.epocaDesportiva = epocaDesportiva;
    }

    public Collection<TreinoDTO> getTreinos() {
        return treinos;
    }

    public void setTreinos(Collection<TreinoDTO> treinos) {
        this.treinos = treinos;
    }

    public Collection<EscalaoDTO> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(Collection<EscalaoDTO> escaloes) {
        this.escaloes = escaloes;
    }

    public Collection<AtletaDTO> getAtletas() {
        return atletas;
    }

    public void setAtletas(Collection<AtletaDTO> atletas) {
        this.atletas = atletas;
    }

    public Collection<SocioDTO> getSocios() {
        return socios;
    }

    public void setSocios(Collection<SocioDTO> socios) {
        this.socios = socios;
    }
}
