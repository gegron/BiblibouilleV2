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
        Author author = new Author.AuthorBuilder().withFirstname(firstname).withLastname(lastname).build();

        // Then
        assertThat(author.getLastname()).isEqualTo(lastname.toUpperCase());
        assertThat(author.getFirstname()).isEqualTo(firstname);
    }

}
