package fr.biblibouille.model.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtils {
    private final static EntityManagerFactory entityManagerFactory;

    static
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("fr.biblibouille.model.jpa");
    }

    /*
     * JEudes: Un entityManager par transaction car non thread safe.
     */
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}
