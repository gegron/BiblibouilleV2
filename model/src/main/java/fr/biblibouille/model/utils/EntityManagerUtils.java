package fr.biblibouille.model.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtils {
    private final static EntityManagerFactory entityManagerFactory;
    private final static EntityManager entityManager;

    static
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("fr.biblibouille.model.jpa");
        entityManager = entityManagerFactory.createEntityManager();
    }

    /*
     * JEudes: Un entityManager par transaction car non thread safe.
     */
    public static EntityManager getEntityManager() {
        // return new EntityManagerFactory.createEntityManager
        return entityManager;
    }

}
