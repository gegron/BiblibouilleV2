package fr.biblibouille.model.handlers;

import com.google.inject.Inject;
import fr.biblibouille.model.Author;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

public class AuthorHandler {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public AuthorHandler(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public Author save(Author author) {

        EntityManager entityManager = entityManagerProvider.get();

        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.getTransaction().commit();

        entityManager.close();

        LOGGER.info(String.format("Author.save: (%s %s)", author.getFirstname(), author.getLastname()));

        return author;
    }

    public List<Author> findAll() {
        EntityManager entityManager = entityManagerProvider.get();
        try {
             return entityManager.createQuery("from Author a order by a.lastname").getResultList();
        }
        finally {
            entityManager.close();
        }
    }

    public Author findOne(Long id) {

        EntityManager entityManager = entityManagerProvider.get();

        try {
            return entityManager.find(Author.class, id);
        }
        finally {
            entityManager.close();
        }
    }


}
