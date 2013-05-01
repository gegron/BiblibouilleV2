package fr.biblibouille.model;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class BookTest {

    @Test
    public void should_create_book() {
        // Given
        Author author = new Author.AuthorBuilder().withFirstname("firstname").withLastname("lastname").build();

        String shelf = "2";
        String collection = "collection";
        String title = "title";

        // When
        Book resultBook = new Book.BookBuilder(title).withCollection(collection).withShelf(shelf).withAuthor(author).build();

        // Then
        assertThat(resultBook.getTitle()).isEqualTo(title);
        assertThat(resultBook.getCollection()).isEqualTo(collection);
        assertThat(resultBook.getShelf()).isEqualTo(shelf);
        assertThat(resultBook.getAuthor()).isEqualTo(author);
    }

}
