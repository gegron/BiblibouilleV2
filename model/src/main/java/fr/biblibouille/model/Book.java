package fr.biblibouille.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.logging.Logger;

@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer"})
@Entity
public class Book implements Serializable {

    @Transient
    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private Long id;
    private String title;
    private String collection;
    private String shelf;
    private User owner;
    private Author author;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @ManyToOne(targetEntity = User.class)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne(targetEntity = Author.class)
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void update(Book newBook) {
        if(newBook.title != null) {
            this.title = newBook.title;
        }

        if(newBook.collection != null) {
            this.collection = newBook.collection;
        }

        if(newBook.shelf != null) {
            this.shelf = newBook.shelf;
        }

        if(newBook.owner != null) {
            this.owner = newBook.owner;
        }

        if(newBook.author != null) {
            this.author = newBook.author;
        }
    }

    public static class BookBuilder {
        private Long id;
        private String title;
        private String collection;
        private String shelf;
        private User owner;
        private Author author;

        public BookBuilder(String title) {
            this.title = title;
        }

        public BookBuilder withCollection(String collection) {
            this.collection = collection;

            return this;
        }

        public BookBuilder withShelf(String shelf) {
            this.shelf = shelf;

            return this;
        }

        public BookBuilder withOwner(User owner) {
            this.owner = owner;

            return this;
        }

        public BookBuilder withAuthor(Author author) {
            this.author = author;

            return this;
        }

        public BookBuilder withId(Long id) {
            this.id = id;

            return this;
        }

        public Book build() {
            return new Book(this);
        }

    }

    protected Book() {

    }

    private Book(BookBuilder bookBuilder) {
        this.id = bookBuilder.id;
        this.title = bookBuilder.title;
        this.collection = bookBuilder.collection;
        this.shelf = bookBuilder.shelf;
        this.author = bookBuilder.author;
        this.owner = bookBuilder.owner;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %s)", title, collection, shelf);
    }

}
