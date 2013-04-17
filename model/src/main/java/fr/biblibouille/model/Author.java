package fr.biblibouille.model;

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
@Entity
public class Author implements Serializable {

    @Transient
    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private Long id;
    private String lastname;
    private String firstname;
    private String libelle;

    // private List<LivreReference> livreReference;
    // TODO: rechercher à quoi correspondre les différentes stratégies de
    // Cascade
//    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL)

    //    @JoinTable(name = "t_auteur_livre",
//            joinColumns = {@JoinColumn(name = "auteur_fk")},
//            inverseJoinColumns = {@JoinColumn(name = "livre_fk")})


//    @OneToMany(cascade = CascadeType.ALL)
//    @ElementCollection()
//    @JoinTable(name = "T_AUTEUR_LIVRE",
//            joinColumns = {@JoinColumn(name = "AUTEUR_ID")},
//            inverseJoinColumns = {@JoinColumn(name = "LIVRE_ID")})

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<Book>();

    public Author() {
    }

    public Author(String lastname, String firstname) {
        this.lastname = lastname.toUpperCase();
        this.firstname = firstname;
    }

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
        miseAJourLibelle();
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        miseAJourLibelle();
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE },
            mappedBy = "author")
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /**
     * Génère le libellé de l'auteur en fonction de son nom et prénom
     */
    private void miseAJourLibelle() {
        StringBuffer libelle = new StringBuffer();

        libelle.append(this.firstname != null ? this.firstname : "");
        if (this.lastname != null && this.firstname != null) {
            libelle.append(" ");
        }
        libelle.append(" " + this.lastname != null ? this.lastname : "");

        this.libelle = libelle.toString();
    }

    public static Author create(String firstname, String lastname) {
        return new AuthorBuilder().withFirstname(firstname).withLastname(lastname).build();
    }

    /**
     * Static constructor for Author
     */
    private static class AuthorBuilder {

        private static Author author;

        private AuthorBuilder() {
            author = new Author();
        }

        private Author build() {
            return author;
        }

        private AuthorBuilder withFirstname(String firstname) {
            author.firstname = firstname;

            return this;
        }

        private AuthorBuilder withLastname(String lastname) {
            author.lastname = lastname;

            return this;
        }
    }

}
