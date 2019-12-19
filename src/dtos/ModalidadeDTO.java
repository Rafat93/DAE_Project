package dtos;

import entities.Atleta;
import entities.Escalao;
import entities.Socio;
import entities.Treino;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ModalidadeDTO implements Serializable {

    private String sigla;
    private String nome;
    private List<Treino> treinos;
    private Set<Escalao> escaloes;
    private List<Atleta> atletas;
    private List<Socio> socios;

    public ModalidadeDTO(String sigla, String nome, List<Treino> treinos, Set<Escalao> escaloes, List<Atleta> atletas, List<Socio> socios) {
        this.sigla = sigla;
        this.nome = nome;
        this.treinos = new LinkedList<>();
        this.escaloes = new HashSet<>();
        this.atletas = new LinkedList<>();
        this.socios = new LinkedList<>();
    }

    public ModalidadeDTO() {
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

    public List<Treino> getTreinos() {
        return treinos;
    }

    public void setTreinos(List<Treino> treinos) {
        this.treinos = treinos;
    }

    public Set<Escalao> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(Set<Escalao> escaloes) {
        this.escaloes = escaloes;
    }

    public List<Atleta> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<Atleta> atletas) {
        this.atletas = atletas;
    }

    public List<Socio> getSocios() {
        return socios;
    }

    public void setSocios(List<Socio> socios) {
        this.socios = socios;
    }
}
