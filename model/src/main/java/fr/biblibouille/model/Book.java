package fr.biblibouille.model;

import fr.biblibouille.model.utils.EntityManagerUtils;

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

    private String etage;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Author author;

//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    private User proprietaire;

    public Book() {

    }

    public Book(String titre, String collection, String etage, Author author) {
        this.titre = titre;
        this.collection = collection;
        this.etage = etage;
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

    public String getEtage() {
        return etage;
    }

    public void setEtage(String etage) {
        this.etage = etage;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

//    /**
//     * Permet de savoir s'il n'y a aucun champs d'initialisé dans l'objet
//     */
//    public Boolean estVide() {
//        boolean estVide = Strings.isNullOrEmpty(this.titre) && Strings.isNullOrEmpty(this.collection)
//                && Strings.isNullOrEmpty(this.etage) && this.author == null;
//
//        return Boolean.valueOf(estVide);
//    }

}
