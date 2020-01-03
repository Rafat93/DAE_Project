package ejbs;

import entities.Administrador;
import entities.Atleta;
import entities.Produto;
import entities.TipoProduto;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;


@Startup
@Singleton(name = "ConfigEJB")
public class ConfigBean {

    @EJB
    private AtletaBean atletaBean;

    @EJB
    private AdministradorBean administradorBean;

    @EJB
    private TreinadorBean treinadorBean;

    @EJB
    private SocioBean socioBean;

    @EJB
    private ModalidadeBean modalidadeBean;

    @EJB
    private ProdutoBean produtoBean;

    @EJB
    private TipoProdutoBean tipoProdutoBean;

    @EJB
    private InscricaoBean inscricaoBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB(){
        try {
            System.out.println("#### Populating DB...");
            System.out.println("CREATING MODALIDADES");
            modalidadeBean.create("Atl","Atletismo","2019/2020");
            modalidadeBean.create("Nat", "Natação", "2019/2020");

            System.out.println("CREATING ATLETAS");
            atletaBean.create(1,"Jose Silva", "jose.silva@ipleiria.com","123", 18,2,2001,15891457,456456789,"Testing");
            atletaBean.create(2,"Sofia Antonia", "sofia.antonia@ipleiria.com","123", 15, 3, 2002,11566327,987456789,"Testing");
            atletaBean.create(3,"Margarida Maria", "maggs@ipleiria.com","123", 15, 3, 1987,11563427,983456789,"Rua da Paz");

            atletaBean.enrollAtletaInModalidade("jose.silva@ipleiria.com", "Atl");
            atletaBean.enrollAtletaInModalidade("maggs@ipleiria.com", "Nat");

            System.out.println("CREATING TREINADORES");
            treinadorBean.create("Rui Norte","123","rui@mail.com",25365);
            treinadorBean.create("Ana Margarida Silva","123","margarida-silva@mail.com",22343);
            treinadorBean.create("Manuel Oliveira","123","oliveira-manuel@mail.com",25555);
            treinadorBean.create("João Filipe Moleira","123","jfmoleira@mail.com",25765);
            treinadorBean.create("Filipa Margarida Mendes","123","m.filipa.m@mail.com",25305);

            System.out.println("CREATING SOCIOS");
            socioBean.create(4,"Carlos Silva","123","carlos.silva@mail.com",12,3,2000,15896327,123456789,"Testing");

            System.out.println("CREATING ADMINS");
            administradorBean.create("a1","a1","a1@admin.com");

            System.out.println("CREATING TIPOPRODUTO");
            tipoProdutoBean.create("Sapatilha");

            System.out.println("CREATING PRODUTOS");
            produtoBean.create("AD20",tipoProdutoBean.findTipoProduto("Sapatilha"),"Adidas Finesse",50,5);
            produtoBean.create("AD50",tipoProdutoBean.findTipoProduto("Sapatilha"),"Adidas Acelerator",100,5);

            System.out.println("CREATING INSCRICOES");
            inscricaoBean.create("INSTEST","João Silva","joão.silva@mail.com",12,12,1993,123456789,123456789,"Rua Teste");

            System.out.println("*********FINISH POPULATING");
        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
