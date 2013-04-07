package fr.biblibouille.model.handlers;

import fr.biblibouille.model.Author;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

import static fr.biblibouille.model.utils.EntityManagerUtils.getEntityManager;

public class AuthorHandler {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public Author save(Author author) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.getTransaction().commit();

        LOGGER.info(String.format("Author.save: (%s %s)", author.getFirstname(), author.getLastname()));

        return author;
    }

    public List<Author> findAll() {
        return getEntityManager().createQuery("from Author").getResultList();
    }

    public Author findOne(Long id) {
        return getEntityManager().find(Author.class, id);
    }

}
