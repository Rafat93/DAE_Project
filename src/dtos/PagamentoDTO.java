package dtos;

import entities.Pagamento;
import entities.Produto;
import entities.User;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class PagamentoDTO  implements Serializable {

    private int id;
    private User utilizador;
    private Produto produto;
    private Date dataLancamento;
    private int quantidade;
    private int precoFinal;
    private Pagamento.EnumEstado estado;
}
