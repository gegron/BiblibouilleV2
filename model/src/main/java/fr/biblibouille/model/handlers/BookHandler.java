package fr.biblibouille.model.handlers;

import fr.biblibouille.model.Book;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

import static fr.biblibouille.model.utils.EntityManagerUtils.getEntityManager;

public class BookHandler {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public Book save(Book book) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(book);

            em.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.severe(String.format("Save error : %s", e.getCause()));

            em.getTransaction().rollback();
        }
        finally {
            em.close();
        }

        LOGGER.info(String.format("Book.save (%s)", book.getTitle()));

        return book;
    }

    public List<Book> findAll() {
        List result;
        EntityManager em = getEntityManager();

        try {
            result = em.createQuery("from " + Book.class.getName()).getResultList();
        } finally {
            em.close();
        }

        return result;
    }

    public Book findOne(long id) {
        Book book;
        EntityManager em = getEntityManager();

        try {
            book = em.find(Book.class, id);
        } finally {
            em.close();
        }

        return book;
    }

}
