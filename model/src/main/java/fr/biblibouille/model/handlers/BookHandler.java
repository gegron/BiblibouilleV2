package fr.biblibouille.model.handlers;

import fr.biblibouille.model.Book;
import fr.biblibouille.model.utils.EntityManagerUtils;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

public class BookHandler {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public Book save(Book book) {

        EntityManager entityManager = EntityManagerUtils.getEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(book);

        entityManager.getTransaction().commit();

        LOGGER.info(String.format("Book.save (%s)", book.getTitre()));

        return book;
    }
}
