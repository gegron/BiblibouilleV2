package fr.biblibouille.model.handlers;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.biblibouille.model.Author;
import fr.biblibouille.model.Book;
import fr.biblibouille.model.User;
import fr.biblibouille.model.module.PersistenceModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class BookHandlerTest extends AbstractIntegrationTest{

    @Inject
    private BookHandler bookHandler;

    @Inject
    private AuthorHandler authorHandler;

    @Inject
    private UserHandler userHandler;

    @Inject
    private Provider<EntityManager> entityManagerProvider;

    @Before
    public void setUp() throws Exception {
        super.setUp();
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

        EntityManager em = entityManagerProvider.get();

        em.getTransaction().begin();
        em.createQuery("delete from " + Book.class.getName()).executeUpdate();
        em.createQuery("delete from " + Author.class.getName()).executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void should_save_livre() {

        EntityManager em = entityManagerProvider.get();

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

    @Test
    public void should_update_title_for_book() {
        // Given
        String updatedTitle = "Updated Title";
        Book newBook = new Book.BookBuilder(updatedTitle).withId(1L).build();

        // When
        bookHandler.update(newBook);

        // Then
        EntityManager entityManager = entityManagerProvider.get();
        Book updatedBook = entityManager.find(Book.class, 1L);

        assertThat(updatedBook.getTitle()).isEqualTo(updatedTitle);
    }


    private Author makeDefaultAuteur() {
        Author author = new Author.AuthorBuilder().withLastname("Dupont").withFirstname("Marcel").build();

        return author;
    }

}
