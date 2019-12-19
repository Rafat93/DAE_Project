package dtos;

import auxiliar.Data;
import entities.Atleta;
import entities.Treinador;
import entities.Treino;

import java.io.Serializable;
import java.util.Set;

public class PresencaDTO implements Serializable {

    private Treino treino;
    private Data dataTreino;
    private Set<Atleta> atletasPresentes;
    private Treinador treinador;


    public PresencaDTO(Treino treino, Data dataTreino, Set<Atleta> atletasPresentes, Treinador treinador) {
        this.treino = treino;
        this.dataTreino = dataTreino;
        this.atletasPresentes = atletasPresentes;
        this.treinador = treinador;
    }

    public PresencaDTO() {
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public Data getDataTreino() {
        return dataTreino;
    }

    public void setDataTreino(Data dataTreino) {
        this.dataTreino = dataTreino;
    }

    public Set<Atleta> getAtletasPresentes() {
        return atletasPresentes;
    }

    public void setAtletasPresentes(Set<Atleta> atletasPresentes) {
        this.atletasPresentes = atletasPresentes;
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }
}
