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

    private String id;
    private String nom;
    private String prenom;
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

    private Set<Book> books = new HashSet<Book>();

    public Author() {
    }

    public Author(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
        miseAJourLibelle();
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
        miseAJourLibelle();
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
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

        libelle.append(this.prenom != null ? this.prenom : "");
        if (this.nom != null && this.prenom != null) {
            libelle.append(" ");
        }
        libelle.append(" " + this.nom != null ? this.nom : "");

        this.libelle = libelle.toString();
    }

}
