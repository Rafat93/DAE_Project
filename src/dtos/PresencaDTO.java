package dtos;

import entities.Atleta;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PresencaDTO implements Serializable {

    private String code;
    private String codeTreino;
    private GregorianCalendar dataTreino;
    private List <String> atletasPresentes;
    private String emailTreinador;


    public PresencaDTO(String code, String codeTreino, GregorianCalendar dataTreino, String emailProfessor) {
        this.code = code;
        this.codeTreino = codeTreino;
        this.dataTreino = dataTreino;
        this.atletasPresentes = new LinkedList<>();
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

    public List<String> getAtletasPresentes() {
        return atletasPresentes;
    }

    public void setAtletasPresentes(List <String> atletasPresentes) {
        this.atletasPresentes = atletasPresentes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
