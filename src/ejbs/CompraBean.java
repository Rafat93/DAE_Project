package ejbs;

import entities.Compra;
import entities.Pagamento;
import entities.Produto;
import entities.User;
import enums.EstadoPagamento;
import enums.TipoPagamentoCompra;
import exceptions.MyEntityAlreadyExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

@Stateless(name = "CompraEJB")
public class CompraBean {

    @EJB
    private PagamentoBean pagamentoBean;

    @PersistenceContext
    private EntityManager em;

    public void create (String code, String userEmail,List<String> codigosProdutos, int dia,int mes,int ano, TipoPagamentoCompra tipoPagamentoCompra, double valorTotal, double valorPago,int numeroParcelas) throws MyEntityAlreadyExistsException {
        try{
            Compra compra = em.find(Compra.class,code);
            if( compra != null){
                throw new MyEntityAlreadyExistsException("Compra com o codigo: " + code + " não existe");
            }
            User user = em.find(User.class,userEmail);
            if(user == null){
                throw new MyEntityNotFoundException("Utilizador com o email: " + userEmail + " não existe.");
            }
            GregorianCalendar dataCompra = new GregorianCalendar(ano,mes,dia);
            compra = new Compra(code,user,dataCompra,tipoPagamentoCompra,valorTotal,valorPago);
            if(tipoPagamentoCompra .equals(TipoPagamentoCompra.PAGAMENTO_PARCIAL)){
                double valorParcela = valorTotal/numeroParcelas;
                GregorianCalendar dataLimite = dataCompra;
                for (int i = 0;i<numeroParcelas;i++){
                    dataLimite.add(Calendar.MONTH,i);
                    Pagamento pagamento = pagamentoBean.create(compra.getCode()+"PG"+i+1,user.getEmail(),compra.getCode(),dataCompra,dataLimite,valorParcela, EstadoPagamento.NAO_PAGO);
                    compra.addPagamento(pagamento);
                }
            }else{
                compra.addPagamento(pagamentoBean.create(compra.getCode()+"PG"+1,user.getEmail(),compra.getCode(),dataCompra,dataCompra,valorTotal,EstadoPagamento.NAO_PAGO));
            }
            List <Produto> produtos = geraProdutos(codigosProdutos);
            compra.setProdutos(produtos);
            em.persist(compra);
        }catch (MyEntityAlreadyExistsException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private List <Produto> geraProdutos(List <String> codigoProdutos) throws MyEntityNotFoundException {
        try {
            List<Produto> produtos = new LinkedList<>();
            for (String code : codigoProdutos) {
                Produto produto = em.find(Produto.class, code);
                if (produto == null || produto.getStock()==0) {
                    throw new MyEntityNotFoundException("Produto com o codigo: " + code + " não existe.");
                }
                produtos.add(produto);
            }
            return produtos;
        }catch (MyEntityNotFoundException e) {
                throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
