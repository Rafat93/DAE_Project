package entities;

import enums.DiasSemana;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Entity
public class Treino {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @ManyToOne
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
    private DiasSemana dia;

    public Treino() {
    }

    public Treino(Time horaInicio, Time horaFim, DiasSemana dia) {
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.dia = dia;
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

    public DiasSemana getDia() {
        return dia;
    }

    public void setDia(DiasSemana dia) {
        this.dia = dia;
    }
}
