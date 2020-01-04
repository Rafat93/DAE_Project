package dtos;

import enums.DiasSemana;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;

public class TreinoDTO implements Serializable {

    private String code;
    private String emailTreinador;
    private String siglaModalidade;
    private String codeGraduacao;
    private String codeEscalao;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private DiasSemana diaSemana;
    private Collection<PresencaDTO> presencas;

    public TreinoDTO(String code, String emailTreinador, String siglaModalidade, String codeGraduacao, String codeEscalao, LocalTime horaInicio, LocalTime horaFim, DiasSemana diaSemana) {
        this.code = code;
        this.emailTreinador = emailTreinador;
        this.siglaModalidade = siglaModalidade;
        this.codeGraduacao = codeGraduacao;
        this.codeEscalao = codeEscalao;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.diaSemana = diaSemana;
        this.presencas = new HashSet<>();
    }

    public TreinoDTO() {
        this.presencas = new HashSet<>();
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

    public String getCodeGraduacao() {
        return codeGraduacao;
    }

    public void setCodeGraduacao(String codeGraduacao) {
        this.codeGraduacao = codeGraduacao;
    }

    public String getCodeEscalao() {
        return codeEscalao;
    }

    public void setCodeEscalao(String codeEscalao) {
        this.codeEscalao = codeEscalao;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
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

    public Collection<PresencaDTO> getPresencas() {
        return presencas;
    }

    public void setPresencas(Collection<PresencaDTO> presencas) {
        this.presencas = presencas;
    }
}
