package fr.biblibouille.model;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class BookTest {

    @Test
    public void should_create_book() {
        // Given
        Author author = new Author("Nom", "Prenom");
        String etage = "2";
        String collection = "collection";
        String title = "title";

        // When
        Book resultBook = Book.create(title, collection, etage, author);

        // Then
        assertThat(resultBook.getTitre()).isEqualTo(title);
        assertThat(resultBook.getCollection()).isEqualTo(collection);
        assertThat(resultBook.getShelf()).isEqualTo(etage);
        assertThat(resultBook.getAuthor()).isEqualTo(author);
    }

}
