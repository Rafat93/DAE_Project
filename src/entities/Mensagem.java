package entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Entity
public class Mensagem {
    @Id
    private String code;
    @NotNull
    private String emailOrigem;
    @NotNull
    private String emailDestino;
    @NotNull
    private String assunto;
    @NotNull
    private String mensagem;
    @NotNull
    private LocalDateTime dataCriacao;
    @NotNull
    private boolean lido;

    public Mensagem() {
    }

    public Mensagem(String emailOrigem, String emailDestino, String assunto, String mensagem) {
        this.emailOrigem = emailOrigem;
        this.emailDestino = emailDestino;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now();
        lido = false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmailOrigem() {
        return emailOrigem;
    }

    public void setEmailOrigem(String emailOrigem) {
        this.emailOrigem = emailOrigem;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isLido() {
        return lido;
    }

    public void setLido(boolean lido) {
        this.lido = lido;
    }
}
