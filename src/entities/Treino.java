package entities;

import enums.DiasSemana;

import javax.persistence.Entity;
import java.sql.Time;
@Entity
public class Treino {

    private Time horaInicio;
    private Time horaFim;
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
