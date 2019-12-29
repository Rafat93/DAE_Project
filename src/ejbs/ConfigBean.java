package ejbs;

import entities.Administrador;
import entities.Atleta;

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

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB(){
        try {
            System.out.println("Seed Db");

            modalidadeBean.create("Atl","Atletismo","2019/2020");

            atletaBean.create(1,"Jose Silva", "jose.silva@ipleiria.com","123", 18,2,2001);
            atletaBean.create(2,"Sofia Antonia", "sofia.antonia@ipleiria.com","123", 15, 3, 2002);

            treinadorBean.create("Rui Norte","123","rui@mail.com",25365);

            socioBean.create(3,"Carlos Silva","carlos.silva@mail.com","123",12,3,2000);
            administradorBean.create("Rafael Correia","rafa@admin.com","rafa123");

        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
