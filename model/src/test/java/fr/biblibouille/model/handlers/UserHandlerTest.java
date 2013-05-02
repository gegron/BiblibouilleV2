package fr.biblibouille.model.handlers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.biblibouille.model.User;
import fr.biblibouille.model.module.PersistenceModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import static org.fest.assertions.Assertions.assertThat;

public class UserHandlerTest {

    private UserHandler userHandler;

    private Provider<EntityManager> entityManagerProvider;

    private String EMAIL_USER_TEST = "userLogin@test.com";

    private String LOGIN_USER_TEST = "userLogin";


    @Before
    public void setUp() throws Exception {

        Injector injector = Guice.createInjector(new PersistenceModule());
        userHandler = injector.getInstance(UserHandler.class);
        entityManagerProvider = injector.getProvider(EntityManager.class);

        save();

    }

    public void save() throws Exception {
        userHandler.save(new User(LOGIN_USER_TEST, "qkfdqdsf", EMAIL_USER_TEST));
    }

    @After
    public void tearDown() throws Exception {
        EntityManager em = entityManagerProvider.get();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from User where email = :email").setParameter("email", EMAIL_USER_TEST).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    @Test
    public void should_save_user() {
        // Given
        String login = "LOGIN_USER_TEST";
        String password = "password";
        String email = "EMAIL_USER_TEST@EMAIL_USER_TEST.com";

        User user = new User(login, password, email);

        // When
        User resultUser = userHandler.save(user);

        // Then
        assertThat(resultUser).isNotNull();
        assertThat(resultUser.getId()).isNotNull();
    }

    @Test
    public void should_find_user_by_email() {
        // Given
        // When
        User user = userHandler.findByEmail(EMAIL_USER_TEST);

        // Then
        assertThat(user).isNotNull();
    }

    @Test
    public void should_find_user_by_login() {
        // Given
        // When
        User result = userHandler.findByLogin(LOGIN_USER_TEST);

        // Then
        assertThat(result).isNotNull();
    }

}
