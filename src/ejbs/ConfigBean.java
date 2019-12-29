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

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB(){
        try {
            System.out.println("Seed Db");

            atletaBean.create(1,"Jose Silva", "jose.silva@ipleiria.com","123", 18,2,2001);
            atletaBean.create(2,"Sofia Antonia", "sofia.antonia@ipleiria.com","123", 15, 3, 2002);

            //administradorBean.create("admin","admin1@teste.com","123456789");

        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
