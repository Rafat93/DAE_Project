package dtos;

import enums.DiasSemana;

import java.io.Serializable;
import java.sql.Time;

public class TreinoDTO implements Serializable {

    private String code;
    private String emailTreinador;
    private String siglaModalidade;
    private int idGraduacao;
    private int idEscalao;
    private Time horaInicio;
    private Time horaFim;
    private DiasSemana diaSemana;

    public TreinoDTO(String code, String nomeTreinador, String siglaModalidade, int idGraduacao, int idEscalao, Time horaInicio, Time horaFim, DiasSemana diaSemana) {
        this.code = code;
        this.emailTreinador = nomeTreinador;
        this.siglaModalidade = siglaModalidade;
        this.idGraduacao = idGraduacao;
        this.idEscalao = idEscalao;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.diaSemana = diaSemana;
    }

    public TreinoDTO() {
    }



    public String getEmailTreinador() {
        return emailTreinador;
    }

    public void setEmailTreinador(String emailTreinador) {
        this.emailTreinador = emailTreinador;
    }

    public String getSiglaModalidade() {
        return siglaModalidade;
    }

    public void setSiglaModalidade(String siglaModalidade) {
        this.siglaModalidade = siglaModalidade;
    }

    public int getIdGraduacao() {
        return idGraduacao;
    }

    public void setIdGraduacao(int idGraduacao) {
        this.idGraduacao = idGraduacao;
    }

    public int getIdEscalao() {
        return idEscalao;
    }

    public void setIdEscalao(int idEscalao) {
        this.idEscalao = idEscalao;
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

    public void setDiaSemana(DiasSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
