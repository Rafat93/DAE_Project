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


    @OneToMany (mappedBy = "modalidade",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Treino> treinos;


    @OneToMany(mappedBy = "modalidade",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Escalao> escaloes;

    @OneToMany
    private List<Atleta> atletas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MODALIDADES_SOCIOS",
            joinColumns =  @JoinColumn(name = "MODALIDADE_SIGLA", referencedColumnName = "SIGLA"),
            inverseJoinColumns = @JoinColumn(name = "SOCIO_EMAIL", referencedColumnName = "EMAIL"))
    private List<Socio> socios;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MODALIDADES_TREINADORES",
            joinColumns =  @JoinColumn(name = "MODALIDADE_SIGLA", referencedColumnName = "SIGLA"),
            inverseJoinColumns = @JoinColumn(name = "TREINADOR_EMAIL", referencedColumnName = "EMAIL"))
    private List<Treinador> treinadores;

    @OneToMany(mappedBy = "modalidade",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Graduacao> graduacoes;

    @NotNull
    private double quotaAnual;


    public Modalidade() {
        this.treinos = new LinkedList<>();
        this.socios = new LinkedList<>();
        this.atletas = new LinkedList<>();
        this.treinadores = new LinkedList<>();
        this.escaloes = new LinkedList<>();
        this.graduacoes = new LinkedList<>();
    }

    public Modalidade (String sigla, String nome, String epocaDesportiva,double quotaAnual){
        this.sigla = sigla;
        this.nome = nome;
        this.epocaDesportiva = epocaDesportiva;
        this.quotaAnual = quotaAnual;
        this.treinos = new LinkedList<>();
        this.socios = new LinkedList<>();
        this.atletas = new LinkedList<>();
        this.treinadores = new LinkedList<>();
        this.escaloes = new LinkedList<>();
        this.graduacoes = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Modalidade && ((Modalidade) o).nome.equals(this.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sigla, nome, treinos, escaloes, atletas, socios,graduacoes);
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

    public double getQuotaAnual() {
        return quotaAnual;
    }

    public void setQuotaAnual(double quotaAnual) {
        this.quotaAnual = quotaAnual;
    }

    public List<Treino> getTreinos() {
        return treinos;
    }


    public List<Escalao> getEscaloes() {
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

    public void addGraduacao (Graduacao graduacao){
        if(!graduacoes.contains(graduacao)) {
            graduacoes.add(graduacao);
        }
    }
    public void removeGraduacao (Graduacao graduacao){
        graduacoes.remove(graduacao);
    }

    public void addEscalao (Escalao escalao){
        if(!escaloes.contains(escalao)) {
            escaloes.add(escalao);
        }
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

    public List<Graduacao> getGraduacoes() {
        return graduacoes;
    }


}
