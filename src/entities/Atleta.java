package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAtletas",
                query = "SELECT a FROM Atleta a ORDER BY a.numeroSocio" // JPQL
        )
})
public class Atleta extends Socio implements Serializable {

    @ManyToMany(mappedBy = "atletas")
    private Set<Modalidade> modalidades;

    public Atleta() {
        super();
    }

    public Atleta(long numeroSocio, String nome, String email,String password, GregorianCalendar dataNascimento,long numIdentificacaoCivil, long numContribuinte, String morada) {
        super(numeroSocio,nome,password,email,dataNascimento,numIdentificacaoCivil,numContribuinte,morada);
        this.modalidades = new LinkedHashSet<>();
    }

    public Set<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public void addModalidade (Modalidade modalidade){
        if (!modalidades.contains(modalidade)){
            modalidades.add(modalidade);
        }
    }

    public void removeModalidade(Modalidade modalidade){
        if (modalidades.contains(modalidade)){
            modalidades.remove(modalidade);
        }
    }
}
