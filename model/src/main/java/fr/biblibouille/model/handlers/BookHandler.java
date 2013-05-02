package fr.biblibouille.model.handlers;

import com.google.inject.Inject;
import fr.biblibouille.model.Book;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

public class BookHandler {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public BookHandler(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }


    public Book save(Book book) {
        EntityManager entityManager = entityManagerProvider.get();
        try {
            entityManager.getTransaction().begin();

            entityManager.persist(book);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.severe(String.format("Save error : %s", e.getCause()));

            entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }

        LOGGER.info(String.format("Book.save (%s)", book.getTitle()));

        return book;
    }

    public List<Book> findAll() {

        EntityManager entityManager = entityManagerProvider.get();

        try {
            return entityManager.createQuery("from " + Book.class.getName()).getResultList();
        }
        finally {
            entityManager.close();
        }

    }

    public Book findOne(long id) {
        EntityManager entityManager = entityManagerProvider.get();

        try {
            return entityManager.find(Book.class, id);
        }
        finally {
            entityManager.close();
        }
    }

}
