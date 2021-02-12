package cleversoft.persistence;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

public class HibernatePersistenceUtil {

    @PersistenceUnit(unitName = "docCorePU")
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("docCorePU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
