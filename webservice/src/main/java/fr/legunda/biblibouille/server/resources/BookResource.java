package fr.legunda.biblibouille.server.resources;

import fr.biblibouille.model.Author;
import fr.biblibouille.model.Book;
import fr.biblibouille.model.handlers.BookHandler;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/book")
public class BookResource {

    private final static BookHandler bookHandler = new BookHandler();

    /**
     * List all book
     *
     * @return
     * @throws IOException
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String findAll() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(bookHandler.findAll());
    }

    /**
     * Bouquin selon identifiant
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findOne(@PathParam("id") long id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(bookHandler.findOne(id));
    }

    /**
     * Ajout d'un bouquin
     *
     */
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@FormParam("title") String title, @FormParam("collection") String collection, @FormParam("etage") String etage) throws IOException {
        Author author = null;
        ObjectMapper mapper = new ObjectMapper();

        Book result = bookHandler.save(Book.create(title, collection, etage, author));

        return Response.ok(mapper.writeValueAsString(result)).build();
    }

}
