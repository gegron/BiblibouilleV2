package fr.biblibouille.model;

import fr.biblibouille.model.enums.TypeUtilisateur;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * L'utilisateur représente un utilisateur du site.
 * Va être utilisé pour identifier ceux qui ont accès à la bibliothèque.
 * <p/>
 * Un utilisateur peut être du type suivant:
 * - Admin
 * - Propriétaire de bibliothèque
 * <p/>
 * Un propriétaire de bibliothèque peut accéder à sa bibliothèque et modifier les ouvrages qui s'y trouvent.
 *
 * @author Legunda
 */
@Entity
public class User {

    private Long id;

    private String login;

    private String password;

    private String email;

    private TypeUtilisateur typeUtilisateur;

    public User() {
    }

    public User(String login, String password, String email, TypeUtilisateur typeUtilisateur) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.typeUtilisateur = typeUtilisateur;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypeUtilisateur getTypeUtilisateur() {
        return typeUtilisateur;
    }

    public void setTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    @Override
    public String toString() {
        return email;
    }

}
