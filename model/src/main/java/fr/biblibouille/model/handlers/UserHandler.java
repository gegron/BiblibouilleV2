package fr.biblibouille.model.handlers;

import com.google.inject.Inject;
import fr.biblibouille.model.User;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

public class UserHandler {

    // FIXME: slf4j
    private Logger LOGGER = Logger.getLogger(UserHandler.class.getName());

    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public UserHandler(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public User save(User user) {

        EntityManager entityManager = entityManagerProvider.get();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.severe("Unable to save user");

            entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }

        LOGGER.info(String.format("User.save: (%s %s)", user.getEmail(), user.getLogin()));

        return user;
    }

    public List<User> findAll() {

        EntityManager entityManager = entityManagerProvider.get();
        try {
            return entityManager.createQuery("from User u").getResultList();
        }
        finally {
            entityManager.close();
        }
    }

    public User findOne(Long id) {
        EntityManager entityManager = entityManagerProvider.get();
        try {
            return entityManager.find(User.class, id);
        }
        finally {
            entityManager.close();
        }
    }

    public User findByEmail(String email) {
        EntityManager entityManager = entityManagerProvider.get();
        User user;
        Query query = entityManager.createQuery("from User u where u.email = :email");
        query.setParameter("email", email);

        try {
            user = (User) query.getSingleResult();
            user.getBooks().size();
        }
        finally {
            entityManager.close();
        }

        return user;
    }

    public User findByLogin(String login) {
        EntityManager entityManager = entityManagerProvider.get();
        Query query = entityManager.createQuery("from User u where u.login = :login");
        query.setParameter("login", login);

        try {
            return (User) query.getSingleResult();
        }
        finally {
            entityManager.close();
        }
    }

}
