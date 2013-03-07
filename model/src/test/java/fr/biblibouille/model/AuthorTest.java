package fr.biblibouille.model;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class AuthorTest {

    @Test
    public void should_create_auteur() {
        // Given
        String nom = "nom";
        String prenom = "prenom";
        String dateNaissance = "dateNaissance";
        String dateMort = "dateMort";
        String image = "image";

        // When
        Author author = new Author(nom, prenom);

        // Then
        assertThat(author.getNom()).isEqualTo(nom);
        assertThat(author.getPrenom()).isEqualTo(prenom);
    }

}
