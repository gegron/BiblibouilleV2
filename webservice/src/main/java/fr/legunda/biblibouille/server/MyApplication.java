package fr.legunda.biblibouille.server;

import fr.biblibouille.model.Author;
import fr.biblibouille.model.Book;
import fr.biblibouille.model.User;
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

//        User owner = new User("gegron", "password", "gerome.egron@gmail.com");

//        em.getTransaction().begin();
//        em.persist(owner);
//        em.persist(new Book("titre 1", "collection", "1", new Author("PROUST", "Marcel"), owner));
//        em.persist(new Book("titre 2", "collection", "12", new Author("ROUSSEAU", "Jean-Jacques"), owner));
//        em.getTransaction().commit();
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