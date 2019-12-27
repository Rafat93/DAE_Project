package entities;

import enums.DiasSemana;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllTreinos",
                query = "SELECT t FROM Treino t ORDER BY t.code" // JPQL
        )
})
public class Treino {

    @Id
    private String code;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TREINADOR_EMAIL")
    private Treinador treinador;

    @ManyToOne
    private Modalidade modalidade;

    @NotNull
    @ManyToOne
    private Graduacao graduacao;

    @ManyToOne
    private Escalao escalao;

    @NotNull
    private Time horaInicio;

    @NotNull
    private Time horaFim;

    @NotNull
    private DiasSemana diaSemana;

    public Treino() {
    }

    public Treino(String code, Treinador treinador, Modalidade modalidade, Graduacao graduacao, Escalao escalao,Time horaInicio, Time horaFim, DiasSemana diaSemana) {
        this.code = code;
        this.treinador = treinador;
        this.modalidade = modalidade;
        this.graduacao = graduacao;
        this.escalao = escalao;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.diaSemana = diaSemana;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Time horaFim) {
        this.horaFim = horaFim;
    }

    public DiasSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiasSemana dia) {
        this.diaSemana = dia;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public Graduacao getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(Graduacao graduacao) {
        this.graduacao = graduacao;
    }

    public Escalao getEscalao() {
        return escalao;
    }

    public void setEscalao(Escalao escalao) {
        this.escalao = escalao;
    }
}
