package fr.biblibouille.model.handlers;

import fr.biblibouille.model.Author;
import fr.biblibouille.model.Book;
import fr.biblibouille.model.User;
import fr.biblibouille.model.utils.EntityManagerUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import java.security.acl.Owner;
import java.util.List;

import static fr.biblibouille.model.utils.EntityManagerUtils.getEntityManager;
import static org.fest.assertions.Assertions.assertThat;

public class BookHandlerTest {

    private BookHandler bookHandler = new BookHandler();

    private EntityManager em = getEntityManager();

    @Before
    public void setUp() throws Exception {
        em.getTransaction().begin();

        loadBook();

        em.getTransaction().commit();
    }

    private void loadBook() {
        User owner = new User("gegron", "password", "gerome.egron@gmail.com");

        em.persist(owner);

        em.persist(new Book("titre 1", "collection", "etage 1", new Author("authorName", "authorFirstName"), owner));
        em.persist(new Book("titre 2", "collection", "etage 1", new Author("authorName", "authorFirstName"), owner));
    }

    @After
    public void tearDown() throws Exception {
        em.getTransaction().begin();
        em.createQuery("delete from " + Book.class.getName()).executeUpdate();
        em.createQuery("delete from " + Author.class.getName()).executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void should_save_livre() {
        // Given
        User owner = em.find(User.class, 1L);

        Author author = makeDefaultAuteur();

        String titre = "titre";
        String collection = "collection";
        String etage = "etage";

        Book book = new Book(titre, collection, etage, author, owner);

        // When
        bookHandler.save(book);

        // Then
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void should_find_book_by_id() {
        // Given

        // When

        // Then
    }

    @Test
    public void should_find_all() {
        // Given

        // When
        List<Book> result = bookHandler.findAll();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
    }

    private Author makeDefaultAuteur() {
        Author author = new Author("Dupont", "Marcel");

        return author;
    }

}
