package dtos;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import static utils.Utilitarios.format;

public class InscricaoAtletaModalidadeDTO {

    private String siglaModalidade;
    private Collection <TreinoDTO> treinos;

    public InscricaoAtletaModalidadeDTO() {
        treinos = new LinkedList<>();
    }

    public InscricaoAtletaModalidadeDTO(String siglaModalidade) {
        this();
        this.siglaModalidade = siglaModalidade;
    }

    public String getSiglaModalidade() {
        return siglaModalidade;
    }

    public void setSiglaModalidade(String siglaModalidade) {
        this.siglaModalidade = siglaModalidade;
    }

    public Collection<TreinoDTO> getTreinos() {
        return treinos;
    }

    public void setTreinos(Collection<TreinoDTO> treinos) {
        this.treinos = treinos;
    }
}
