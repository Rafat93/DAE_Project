package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSocios",
                query = "SELECT s FROM Socio s ORDER BY s.nome" // JPQL
        )
})
public class Socio extends User implements Serializable {

    @NotNull
    @Column(unique=true)
    private long numeroSocio;

    @NotNull
    private GregorianCalendar dataNascimento;

    @ManyToMany(mappedBy = "socios")
    private Set<Modalidade> modalidades;

    //

    public Socio() {
    }

    public Socio(long numeroSocio, String nome, String password, String email, GregorianCalendar dataNascimento) {
        super(nome, password, email);
        this.numeroSocio = numeroSocio;
        this.dataNascimento = dataNascimento;
        this.modalidades = new LinkedHashSet<>();
    }

    public Set<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public long getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(long numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public GregorianCalendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(GregorianCalendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void addModalidade (Modalidade modalidade){
        if (!modalidades.contains(modalidade)){
            modalidades.add(modalidade);
        }
    }

    public void removeModalidade (Modalidade modalidade){
        if (modalidades.contains(modalidade)){
            modalidades.remove(modalidade);
        }
    }


}
