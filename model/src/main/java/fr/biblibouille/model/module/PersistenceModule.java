package fr.biblibouille.model.module;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import fr.biblibouille.model.handlers.AuthorHandler;
import fr.biblibouille.model.handlers.BookHandler;
import fr.biblibouille.model.handlers.UserHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceModule extends AbstractModule {


    private EntityManagerFactory entityManagerFactory;

    @Override
    protected void configure() {
        entityManagerFactory = Persistence.createEntityManagerFactory("java:comp/env/jdbc/cloudbees.biblibouilledb");
//        entityManagerFactory = Persistence.createEntityManagerFactory("fr.biblibouille.model.jpa");
        bind(AuthorHandler.class);
        bind(BookHandler.class);
        bind(UserHandler.class);

    }

    @Provides
    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}
