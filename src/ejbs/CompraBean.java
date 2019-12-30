package ejbs;

import entities.*;
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

    @EJB
    private ProdutoBean produtoBean;

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

    public List<Compra> all(){
        try{
            return (List<Compra>) em.createNamedQuery("getAllCompras").getResultList();
        }catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_COMPRAS", e);
        }
    }

    public Compra findCompra(String code){
        try{
            return em.find(Compra.class,code);
        }catch (Exception e) {
            throw new EJBException("ERROR_FINDING_COMPRA", e);
        }
    }

    public void delete (String code){
        try {
            em.remove(findCompra(code));
        }catch (Exception e){
            throw new EJBException("ERROR_DELETING_COMPRA",e);
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
                produtoBean.decrementStock(code,1);
            }
            return produtos;
        }catch (MyEntityNotFoundException e) {
                throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void addProduto(String codeProduto, String codeCompra) throws MyEntityNotFoundException {
        try{
            Produto produto = em.find(Produto.class,codeProduto);
            if(produto == null){
                throw new MyEntityNotFoundException("Produto com o codigo "+codeProduto+" não existe");
            }
            Compra compra = em.find(Compra.class,codeCompra);
            if(compra == null){
                throw new MyEntityNotFoundException("Compra com o codigo "+codeCompra+" não existe");
            }
            if (produto.getStock() > 0) {
                compra.addProduto(produto);
                compra.setValorTotal(compra.getValorTotal() + produto.getPrecoEmEuros());
                produtoBean.decrementStock(produto.getCode(),1);
            }else{
                throw new NullPointerException("Não tem stock");
            }
        }catch (MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void removeProduto(String codeProduto, String codeCompra) throws MyEntityNotFoundException {
        try{
            Produto produto = em.find(Produto.class,codeProduto);
            if(produto == null){
                throw new MyEntityNotFoundException("Produto com o codigo "+codeProduto+" não existe");
            }
            Compra compra = em.find(Compra.class,codeCompra);
            if(compra == null){
                throw new MyEntityNotFoundException("Compra com o codigo "+codeCompra+" não existe");
            }
            compra.removeProduto(produto);
            compra.setValorTotal(compra.getValorTotal() - produto.getPrecoEmEuros());
            produtoBean.incrementStock(produto.getCode(),1);
        }catch (MyEntityNotFoundException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
