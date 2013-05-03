package fr.biblibouille.batch.oneshot;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import fr.biblibouille.model.Author;
import fr.biblibouille.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.google.common.io.Resources.getResource;

public class SqlGenerator {

    private Logger LOGGER = LoggerFactory.getLogger(SqlGenerator.class);

    private String filename;

    private String filenameOutAuthor = "insert_authors.sql";

    private String filenameOutBook = "insert_books.sql";

    private Map<Author, List<Book>> workingMap = Maps.newHashMap();

    public SqlGenerator(String filename) {
        this.filename = filename;
    }

    public void buildAuthors() {
        try {
            URL resource = getResource(this.filename);

            CSVReader reader = new CSVReader(new FileReader(resource.getFile()), ';', '@', 1);

            String[] line;
            while ((line = reader.readNext()) != null) {
                Author author = new Author.AuthorBuilder().withLastname(line[0]).withFirstname(line[1]).build();
                // TODO: managing of the owner ??
                Book book = new Book.BookBuilder(line[2]).withCollection(line[3]).withShelf(line[4]).withAuthor(author).build();

                if (!workingMap.containsKey(author)) {
                    workingMap.put(author, Lists.<Book>newArrayList());
                }

                List<Book> books = workingMap.get(author);
                books.add(book);
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.warn("Cannot close the file %s", filename);
        }
    }

    private void displayAllSql() {
        List<Author> authors = Lists.newArrayList(workingMap.keySet());

        Collections.sort(authors, new Comparator<Author>() {
            @Override
            public int compare(Author o1, Author o2) {
                return o1.getLastname().compareTo(o2.getLastname());
            }
        });

        for (Author author : authors) {
            System.out.println(String.format("INSERT INTO AUTHOR(firstname, lastname) VALUES ('%s', '%s');", author.getFirstname(), author.getLastname()));

            List<Book> books = workingMap.get(author);

            StringBuilder displayBook = new StringBuilder();

            for (Book book : books) {
                displayBook.append(String.format("[%s]", book));
                String ownerId = "1";
                System.out.println(String.format("INSERT INTO BOOK(collection, shelf, title, author_id, owner_id) VALUES SELECT '%s', '%s', '%s', a.id, '%s' FROM AUTHOR a WHERE a.firstname = '%s' and a.lastname = '%s';", book.getCollection(), book.getShelf(), book.getTitle(), ownerId, author.getFirstname(), author.getLastname()));
            }
        }
    }

    private void displayAll() {
        List<Author> authors = Lists.newArrayList(workingMap.keySet());

        Collections.sort(authors, new Comparator<Author>() {
            @Override
            public int compare(Author o1, Author o2) {
                return o1.getLastname().compareTo(o2.getLastname());
            }
        });

        for (Author author : authors) {
            List<Book> books = workingMap.get(author);

            StringBuilder displayBook = new StringBuilder();

            for (Book book : books) {
                displayBook.append(String.format("[%s]", book));
            }


            System.out.println(String.format("%s: %s", author, displayBook));
        }
    }

    private void writeFiles() {
        try {
            BufferedWriter authorWriter = Files.newWriter(new File(getResource(filenameOutAuthor).getPath()), Charset.defaultCharset());
            BufferedWriter bookWriter = Files.newWriter(new File(getResource(filenameOutBook).getPath()), Charset.defaultCharset());

            List<Author> authors = Lists.newArrayList(workingMap.keySet());

            Collections.sort(authors, new Comparator<Author>() {
                @Override
                public int compare(Author o1, Author o2) {
                    return o1.getLastname().compareTo(o2.getLastname());
                }
            });

            for (Author author : authors) {
                authorWriter.write(String.format("INSERT INTO AUTHOR(firstname, lastname) VALUES (\"%s\", \"%s\");\n", author.getFirstname(), author.getLastname()));

                for (Book book : workingMap.get(author)) {
                    String ownerId = "1";
                    bookWriter.write(String.format("INSERT INTO BOOK(collection, shelf, title, author_id, owner_id) SELECT \"%s\", \"%s\", \"%s\", a.id, %s FROM AUTHOR a WHERE a.firstname = \"%s\" and a.lastname = \"%s\";\n", book.getCollection(), book.getShelf(), book.getTitle(), ownerId, author.getFirstname(), author.getLastname()));
                }
            }

            authorWriter.flush();
            authorWriter.close();

            bookWriter.flush();
            bookWriter.close();
        } catch (FileNotFoundException e) {
            LOGGER.warn("File not found: %s", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Error while write values: %s", e.getMessage());
        }
    }

    public static void main(String[] args) {
        SqlGenerator sqlGenerator = new SqlGenerator("BibliJess.csv");

        sqlGenerator.buildAuthors();

//        sqlGenerator.displayAllSql();

//        sqlGenerator.displayAll();
//        sqlGenerator.displayAllSql();
        sqlGenerator.writeFiles();
    }

}
