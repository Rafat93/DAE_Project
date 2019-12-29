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
        System.out.println("Seed Db");

        try {
            Administrador admin = administradorBean.create("admin","admin@teste.com","123456789");
            Atleta atleta1 = atletaBean.create(1,"Jose Silva", "jose.silva@ipleiria.com","123", 18,2,2001);
            Atleta atleta2 = atletaBean.create(2,"Sofia Antonia", "sofia.antonia@ipleiria.com","123", 15, 3, 2002);
        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
