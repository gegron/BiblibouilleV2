package fr.biblibouille.model.handlers;

import com.google.inject.Inject;
import fr.biblibouille.model.Book;
import fr.biblibouille.model.User;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
        }
        catch (Exception e) {
            LOGGER.severe(String.format("Save error : %s", e.getCause()));

            entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }

        LOGGER.info(String.format("Book.save (%s)", book.getTitle()));

        return book;
    }

    public List<Book> findAll(User user) {
        EntityManager entityManager = entityManagerProvider.get();

        try {
            TypedQuery<Book> query = entityManager.createQuery("from " + Book.class.getName() + " b where b.owner=:user", Book.class);

            query.setParameter("user", user);

            return query.getResultList();
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

    public void update(Book newBook) {
        EntityManager entityManager = entityManagerProvider.get();

        try {
            entityManager.getTransaction().begin();

            Book book = entityManager.find(Book.class, newBook.getId());
            book.update(newBook);

            entityManager.merge(book);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }
    }

}
