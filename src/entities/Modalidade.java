package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "MODALIDADES",
            uniqueConstraints = @UniqueConstraint(columnNames = {"NOME"}))
@NamedQueries({
        @NamedQuery(
                name = "getAllModalidades",
                query = "SELECT m FROM Modalidade m ORDER BY m.nome"
        )
})
public class Modalidade {

    @Id
    private String sigla;

    @NotNull
    private String nome;

    @NotNull
    private String epocaDesportiva;

    @NotNull
    @OneToMany
    private List<Treino> treinos;

    @NotNull
    @ManyToMany
    private Set<Escalao> escaloes;

    @OneToMany
    private List<Atleta> atletas;

    @ManyToMany
    private List<Socio> socios;

    @ManyToMany
    private List<Treinador> treinadores;


    public Modalidade() {
        this.treinos = new LinkedList<>();
        this.socios = new LinkedList<>();
        this.atletas = new LinkedList<>();
        this.treinadores = new LinkedList<>();
        this.escaloes = new HashSet<>();
    }

    public Modalidade (String sigla, String nome, String epocaDesportiva){
        this();
        this.sigla = sigla;
        this.nome = nome;
        this.epocaDesportiva=epocaDesportiva;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Modalidade && ((Modalidade) o).nome.equals(this.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sigla, nome, treinos, escaloes, atletas, socios);
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


    public Set<Escalao> getEscaloes() {
        return escaloes;
    }


    public List<Atleta> getAtletas() {
        return atletas;
    }


    public List<Socio> getSocios() {
        return socios;
    }

    public List<Treinador> getTreinadores() {
        return treinadores;
    }

    public void addSocio (Socio socio){
        if (!socios.contains(socio)){
            socios.add(socio);
        }
    }

    public void removeSocio (Socio socio){
        socios.remove(socio);
    }

    public void addAtleta (Atleta atleta){
        if (!atletas.contains(atleta)){
            atletas.add(atleta);
        }
    }

    public void removeAtleta (Atleta atleta){
        atletas.remove(atleta);
    }

    public void addEscalao (Escalao escalao){
        escaloes.add(escalao);
    }
    public void removeEscalao (Escalao escalao){
        escaloes.remove(escalao);
    }

    public void addTreino (Treino treino){
        if (!treinos.contains(treino)){
            treinos.add(treino);
        }
    }

    public void removeTreino (Treino treino){
        treinos.remove(treino);
    }

    public void addTreinador (Treinador treinador){
        if (!treinadores.contains(treinador)){
            treinadores.add(treinador);
        }
    }

    public void removeTreinador (Treinador treinador){
        treinadores.remove(treinador);
    }

    public String getEpocaDesportiva() {
        return epocaDesportiva;
    }

    public void setEpocaDesportiva(String epocaDesportiva) {
        this.epocaDesportiva = epocaDesportiva;
    }
}
