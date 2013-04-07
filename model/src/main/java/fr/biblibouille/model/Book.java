package fr.biblibouille.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.logging.Logger;

@Entity
public class Book implements Serializable {

    @Transient
    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private Long id;

    private String titre;

    private String collection;

    private String shelf;

    //    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Author author;

//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    private User proprietaire;

    public Book() {

    }

    public Book(String titre, String collection, String shelf, Author author) {
        this.titre = titre;
        this.collection = collection;
        this.shelf = shelf;
        this.author = author;
//        this.author = author;
//        this.proprietaire = proprietaire;
    }

//@ManyToOne(optional = false)
    //private User proprietaire;

    // @ManyToMany
    // @JoinTable(name = "LIVRE_AUTEUR", joinColumns = { @JoinColumn(name =
    // "LIVRE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTEUR_ID") })
    // @LazyCollection(LazyCollectionOption.TRUE)
    // public List<Author> auteurs = new ArrayList<Author>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public static Book create(String title, String collection, String etage, Author author) {
        return new BookBuilder()
                .withTitle(title)
                .withCollection(collection)
                .withEtage(etage)
                .withAuthor(author)
                .build();
    }

    // TODO: check if not better solution/implementation
    private static class BookBuilder {

        private Book book;

        private BookBuilder() {
            this.book = new Book();
        }

        // TODO: voir comment detruire book une fois qu'il est retourné
        private Book build() {
            return book;
        }

        private BookBuilder withTitle(String title) {
            book.titre = title;

            return this;
        }

        private BookBuilder withCollection(String collection) {
            book.collection = collection;

            return this;
        }

        private BookBuilder withEtage(String etage) {
            book.shelf = etage;

            return this;
        }

        private BookBuilder withAuthor(Author author) {
            book.author = author;

            return this;
        }

    }

//    /**
    //     * Permet de savoir s'il n'y a aucun champs d'initialisé dans l'objet
//     */
//    public Boolean estVide() {
//        boolean estVide = Strings.isNullOrEmpty(this.titre) && Strings.isNullOrEmpty(this.collection)
//                && Strings.isNullOrEmpty(this.shelf) && this.author == null;
//
//        return Boolean.valueOf(estVide);
//    }

}
