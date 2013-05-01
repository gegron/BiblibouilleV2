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

    private AuthorHandler authorHandler = new AuthorHandler();

    private UserHandler userHandler = UserHandler.create();

    private EntityManager em = getEntityManager();

    @Before
    public void setUp() throws Exception {
        loadBook();
    }

    private void loadBook() {
        User owner = new User("gegron", "password", "gerome.egron@gmail.com");
        owner = userHandler.save(owner);

        Author author = authorHandler.save(new Author.AuthorBuilder().withLastname("authorName").withFirstname("authorFirstName").build());

        Book b1 = new Book.BookBuilder("titre 1").withCollection("collection").withShelf("1").withAuthor(author).build();
        Book b2 = new Book.BookBuilder("titre 2").withCollection("collection").withShelf("1").withAuthor(author).build();

        author.getBooks().add(b1);
        owner.addBook(b1);
        bookHandler.save(b1);

        author.getBooks().add(b2);
        owner.addBook(b2);
        bookHandler.save(b2);
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

        Author author = authorHandler.findAll().get(0);

        String titre = "titre";
        String collection = "collection";
        String shelf = "shelf";

        Book book = new Book.BookBuilder(titre).withCollection(collection).withShelf(shelf).withAuthor(author).withOwner(owner).build();

        // When
        bookHandler.save(book);

        // Then
        assertThat(book.getId()).isNotNull();
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
        Author author = new Author.AuthorBuilder().withLastname("Dupont").withFirstname("Marcel").build();

        return author;
    }

}
