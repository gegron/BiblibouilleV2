package fr.biblibouille.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * En héritant de la classe Model, on pourrait s'éviter de gérer un Id par
 * exemple. Mais c'est plus intéressant de le faire
 *
 * @author Legunda
 */
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer"})
@Entity
public class Author implements Serializable {

    @Transient
    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private Long id;
    private String lastname;
    private String firstname;

    private Set<Book> books = new HashSet<Book>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname.toUpperCase();
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE },
            mappedBy = "author")
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /**
     * Static constructor for Author
     */
    public static class AuthorBuilder {

        private String lastname;
        private String firstname;

        public AuthorBuilder() {
        }

        public AuthorBuilder withFirstname(String firstname) {
            this.firstname = firstname;

            return this;
        }

        public AuthorBuilder withLastname(String lastname) {
            this.lastname = lastname.toUpperCase();

            return this;
        }

        public Author build() {
            return new Author(this);
        }

    }

    protected Author() {
    }

    private Author(AuthorBuilder authorBuilder) {
        this.lastname = authorBuilder.lastname;
        this.firstname = authorBuilder.firstname;
    }

}
