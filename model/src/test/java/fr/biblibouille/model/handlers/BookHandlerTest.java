package fr.biblibouille.model.handlers;

import fr.biblibouille.model.Author;
import fr.biblibouille.model.Book;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class BookHandlerTest {

    private BookHandler bookHandler = new BookHandler();

    @Test
    public void should_save_livre() {
        // Given
        Author author = makeDefaultAuteur();
        String titre = "titre";
        String collection = "collection";
        String etage = "etage";

        Book book = new Book(titre, collection, etage, author);

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

    private Author makeDefaultAuteur() {
        Author author = new Author("Dupont", "Marcel");

        return author;
    }

}
