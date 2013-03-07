package fr.biblibouille.model.handlers;

import fr.biblibouille.model.Author;
import fr.biblibouille.model.utils.EntityManagerUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

public class AuthorHandler {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public Author save(Author author) {
        EntityManager entityManager = EntityManagerUtils.getEntityManager();

        entityManager.getTransaction().begin();

        LOGGER.info(String.format("Author.save: (%s %s)", author.getPrenom(), author.getNom()));
        entityManager.persist(author);

        entityManager.getTransaction().commit();
        //entityManager.close();

        return author;
    }

    public List<Author> findAll() {
        EntityManager entityManager = EntityManagerUtils.getEntityManager();

        entityManager.getTransaction().begin();

        List<Author> result = entityManager.createQuery("from Author").getResultList();

        entityManager.getTransaction().commit();

        return result;
    }


}
