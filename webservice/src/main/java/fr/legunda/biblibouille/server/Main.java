package fr.legunda.biblibouille.server;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import fr.biblibouille.model.Author;
import fr.biblibouille.model.Book;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

import static fr.biblibouille.model.utils.EntityManagerUtils.getEntityManager;

public class Main {

    private static Logger LOGGER = LoggerFactory.getLogger("Serveur");

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(9998).build();
    }

    public static final URI BASE_URI = getBaseURI();

    protected static HttpServer startServer() throws IOException {
        LOGGER.info("Starting grizzly...");

        ResourceConfig rc = new PackagesResourceConfig("fr.legunda.biblibouille.server.resources");

        return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
    }

    public static void main(String[] args) throws IOException {
        loadBookForExample();

        HttpServer httpServer = startServer();

        LOGGER.info(String.format("Jersey app started with WADL available at %sapplication.wadl\nTry out %shelloworld\nHit enter to stop it...", BASE_URI, BASE_URI));

        System.in.read();

        httpServer.stop();

    }

    private static void loadBookForExample() {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(new Book("titre 1", "collection", "etage 1", new Author("authorName", "authorFirstName")));
        em.persist(new Book("titre 2", "collection", "etage 1", new Author("authorName", "authorFirstName")));
        em.getTransaction().commit();
    }

}
