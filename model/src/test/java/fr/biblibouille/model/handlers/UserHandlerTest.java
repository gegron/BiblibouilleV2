package fr.biblibouille.model.handlers;

import fr.biblibouille.model.User;
import fr.biblibouille.model.utils.EntityManagerUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.fest.assertions.Assertions.assertThat;

public class UserHandlerTest {

    private UserHandler userHandler = UserHandler.create();

    private String EMAIL_USER_TEST = "userLogin@test.com";

    private String LOGIN_USER_TEST = "userLogin";


    @Before
    public void setUp() throws Exception {
        userHandler.save(new User(LOGIN_USER_TEST, "qkfdqdsf", EMAIL_USER_TEST));
    }

    @After
    public void tearDown() throws Exception {
        EntityManager em = EntityManagerUtils.getEntityManager();

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
