package fr.biblibouille.model;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class AuthorTest {

    @Test
    public void should_create_auteur() {
        // Given
        String lastname = "nom";
        String firstname = "prenom";

        // When
        Author author = Author.create(firstname, lastname);

        // Then
        assertThat(author.getLastname()).isEqualTo(lastname);
        assertThat(author.getFirstname()).isEqualTo(firstname);
    }

}
