package fr.biblibouille.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

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
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
@Entity
public class User implements Principal, Serializable {

    private Long id;

    private String login;

    private String password;

    private String email;

    private Set<Book> books = new HashSet<Book>();

    public User() {
    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
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

    @Override
    public String toString() {
        return email;
    }

    @Transient
    @Override
    public String getName() {
        return getEmail();
    }

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "owner")
    public Set<Book> getBooks() {
        return books;
    }

    private void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        book.setOwner(this);

        books.add(book);
    }

}
