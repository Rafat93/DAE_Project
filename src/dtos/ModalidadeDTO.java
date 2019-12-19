package dtos;

import entities.Atleta;
import entities.Escalao;
import entities.Socio;
import entities.Treino;

import java.io.Serializable;
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
        this.treinos = treinos;
        this.escaloes = escaloes;
        this.atletas = atletas;
        this.socios = socios;
    }

    public ModalidadeDTO() {
    }
}
