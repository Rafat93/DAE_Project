package dtos;

import entities.Atleta;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Set;

public class PresencaDTO implements Serializable {

    private String code;
    private String codeTreino;
    private GregorianCalendar dataTreino;
    private Set<Atleta> atletasPresentes;
    private String emailTreinador;


    public PresencaDTO(String code, String codeTreino, GregorianCalendar dataTreino, Set<Atleta> atletasPresentes, String emailProfessor) {
        this.code = code;
        this.codeTreino = codeTreino;
        this.dataTreino = dataTreino;
        this.atletasPresentes = atletasPresentes;
        this.emailTreinador = emailProfessor;
    }

    public PresencaDTO() {
    }

    public String getCodeTreino() {
        return codeTreino;
    }

    public void setCodeTreino(String codeTreino) {
        this.codeTreino = codeTreino;
    }

    public String getEmailTreinador() {
        return emailTreinador;
    }

    public void setEmailTreinador(String emailTreinador) {
        this.emailTreinador = emailTreinador;
    }

    public GregorianCalendar getDataTreino() {
        return dataTreino;
    }

    public void setDataTreino(GregorianCalendar dataTreino) {
        this.dataTreino = dataTreino;
    }

    public Set<Atleta> getAtletasPresentes() {
        return atletasPresentes;
    }

    public void setAtletasPresentes(Set<Atleta> atletasPresentes) {
        this.atletasPresentes = atletasPresentes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
