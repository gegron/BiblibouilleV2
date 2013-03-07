package fr.biblibouille.model;

import fr.biblibouille.model.enums.TypeUtilisateur;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class UserTest {
    
    @Test
    public void should_create_utilisateur() {
        // Given
        TypeUtilisateur typeUtilisateur = TypeUtilisateur.PROPRIETAIRE;
        String email = "proprietaire@email.com";
        String login = "proprietaire";
        String password = "password";

        // When
        User user = new User(login, password, email, typeUtilisateur);

        // Then
        assertThat(user.getLogin()).isEqualTo(login);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getTypeUtilisateur()).isEqualTo(typeUtilisateur);
    }
    
}
