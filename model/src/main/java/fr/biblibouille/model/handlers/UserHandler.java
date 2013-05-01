package fr.biblibouille.model.handlers;

import fr.biblibouille.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

import static fr.biblibouille.model.utils.EntityManagerUtils.getEntityManager;

public class UserHandler {

    // FIXME: slf4j
    private Logger LOGGER = Logger.getLogger(UserHandler.class.getName());

    public User save(User user) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.severe("Unable to save user");

            em.getTransaction().rollback();
        }
        finally {
            em.close();
        }

        LOGGER.info(String.format("User.save: (%s %s)", user.getEmail(), user.getLogin()));

        return user;
    }

    public List<User> findAll() {
        List results;
        EntityManager em = getEntityManager();

        try {
            results = em.createQuery("from User u").getResultList();
        }
        finally {
            em.close();
        }

        return results;
    }

    public User findOne(Long id) {
        EntityManager em = getEntityManager();
        User user;

        try {
            user = em.find(User.class, id);
        }
        finally {
            em.close();
        }

        return user;
    }

    public User findByEmail(String email) {
        User user;
        EntityManager em = getEntityManager();

        Query query = em.createQuery("from User u where u.email = :email");
        query.setParameter("email", email);

        try {
            user = (User) query.getSingleResult();
            user.getBooks().size();
        }
        finally {
            em.close();
        }

        return user;
    }

    public User findByLogin(String login) {
        User user;
        EntityManager em = getEntityManager();

        Query query = em.createQuery("from User u where u.login = :login");
        query.setParameter("login", login);

        try {
            user = (User) query.getSingleResult();
        }
        finally {
            em.close();
        }

        return user;
    }

    // Constrcuctor factory

    private UserHandler() {
    }

    public static UserHandler create() {
        return new UserHandler();
    }

}
