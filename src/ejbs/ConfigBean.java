package ejbs;

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

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB(){
        System.out.println("Seed Db");

        try {
            Atleta atleta1 = atletaBean.create(1,"Jose Silva", "jose.silva@ipleiria.com","123", 18);
            Atleta atleta2 = atletaBean.create(2,"Sofia Antonia", "sofia.antonia@ipleiria.com","123", 15);
        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
