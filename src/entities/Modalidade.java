package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    //Falta colocar a epoca desportiva

    @Id
    private String sigla;

    @NotNull
    private String nome;

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

    public void addSocio (Socio socio){
        if (!socios.contains(socio)){
            socios.add(socio);
        }
    }

    public void removeSocio (Socio socio){
        if (socios.contains(socio)){
            socios.remove(socio);
        }
    }

    public void addAtleta (Atleta atleta){
        if (!atletas.contains(atleta)){
            atletas.add(atleta);
        }
    }

    public void removeAtleta (Atleta atleta){
        if (atletas.contains(atleta)){
            atletas.remove(atleta);
        }
    }

    public void addEscalao (Escalao escalao){
        if (!escaloes.contains(escalao)){
            escaloes.add(escalao);
        }
    }
    public void removeEscalao (Escalao escalao){
        if (escaloes.contains(escalao)){
            escaloes.remove(escalao);
        }
    }

    public void addTreino (Treino treino){
        if (!treinos.contains(treino)){
            treinos.add(treino);
        }
    }

    public void removeTreino (Treino treino){
        if (treinos.contains(treino)){
            treinos.remove(treino);
        }
    }
}
