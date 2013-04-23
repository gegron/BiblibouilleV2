package fr.biblibouille.model.handlers;

import fr.biblibouille.model.Book;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

import static fr.biblibouille.model.utils.EntityManagerUtils.getEntityManager;

public class BookHandler {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public Book save(Book book) {
        EntityManager entityManager = getEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(book);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.info(String.format("Save error"));
            entityManager.getTransaction().rollback();
        }

        LOGGER.info(String.format("Book.save (%s)", book.getTitre()));

        return book;
    }

    public List<Book> findAll() {
        return getEntityManager().createQuery("from " + Book.class.getName()).getResultList();
    }

    public Book findOne(long id) {
        return getEntityManager().find(Book.class, id);
    }

}
