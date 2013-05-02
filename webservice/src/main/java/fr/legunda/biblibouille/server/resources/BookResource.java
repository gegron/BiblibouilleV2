package fr.legunda.biblibouille.server.resources;

import com.google.inject.Inject;
import fr.biblibouille.model.Author;
import fr.biblibouille.model.Book;
import fr.biblibouille.model.User;
import fr.biblibouille.model.handlers.AuthorHandler;
import fr.biblibouille.model.handlers.BookHandler;
import fr.biblibouille.model.handlers.UserHandler;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/book")
public class BookResource extends HttpServlet {

    private final BookHandler bookHandler;

    private final AuthorHandler authorHandler;

    private UserHandler userHandler ;

    @Inject
    public BookResource(BookHandler bookHandler, AuthorHandler authorHandler, UserHandler userHandler) {
        this.bookHandler = bookHandler;
        this.authorHandler = authorHandler;
        this.userHandler = userHandler;
    }

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
    public Response post(@FormParam("title") String title, @FormParam("collection") String collection, @FormParam("shelf") String shelf, @FormParam("authorId") long authorId) throws IOException {
        Author author = authorHandler.findOne(authorId);
        User owner = userHandler.findByEmail("j.thoulouse@gmail.com");

        Book book = new Book.BookBuilder(title).withCollection(collection).withShelf(shelf).withAuthor(author).withOwner(owner).build();

        Book result = bookHandler.save(book);

        ObjectMapper mapper = new ObjectMapper();

        return Response.ok(mapper.writeValueAsString(result)).build();
    }

}
