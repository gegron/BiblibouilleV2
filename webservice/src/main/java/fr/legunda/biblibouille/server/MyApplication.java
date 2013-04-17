package fr.legunda.biblibouille.server;

import fr.biblibouille.model.Author;
import fr.biblibouille.model.Book;
import fr.legunda.biblibouille.server.resources.AuthorResource;
import fr.legunda.biblibouille.server.resources.BookResource;
import fr.legunda.biblibouille.server.resources.HelloWorldRessource;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

import static fr.biblibouille.model.utils.EntityManagerUtils.getEntityManager;

public class MyApplication extends Application {

    static {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(new Book("titre 1", "collection", "1", new Author("PROUST", "Marcel")));
        em.persist(new Book("titre 2", "collection", "12", new Author("ROUSSEAU", "Jean-Jacques")));
        em.getTransaction().commit();
    }

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();

        // register root resource
        classes.add(AuthorResource.class);
        classes.add(BookResource.class);
        classes.add(HelloWorldRessource.class);

        return classes;
    }

}