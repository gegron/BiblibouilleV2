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

        entityManager.close();

        LOGGER.info(String.format("Author.save: (%s %s)", author.getFirstname(), author.getLastname()));

        return author;
    }

    public List<Author> findAll() {
        List authors;
        EntityManager em = getEntityManager();

        try {
             authors = em.createQuery("from Author a order by a.lastname").getResultList();
        }
        finally {
            em.close();
        }

        return authors;
    }

    public Author findOne(Long id) {
        Author author;
        EntityManager em = getEntityManager();

        try {
            author = em.find(Author.class, id);
        }
        finally {
            em.close();
        }

        return author;
    }


}
