package fr.legunda.biblibouille.server.resources;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import fr.biblibouille.model.Author;
import fr.biblibouille.model.Book;
import fr.biblibouille.model.User;
import fr.biblibouille.model.handlers.AuthorHandler;
import fr.biblibouille.model.handlers.BookHandler;
import fr.biblibouille.model.handlers.UserHandler;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Logger;

@Path("/book")
public class BookResource extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private final BookHandler bookHandler;

    private final AuthorHandler authorHandler;

    private UserHandler userHandler;

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
    public String findAll(@Context HttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        return mapper.writeValueAsString(bookHandler.findAll(user));
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
     */
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@Context HttpServletRequest request, @FormParam("title") String title, @FormParam("collection") String collection, @FormParam("shelf") String shelf, @FormParam("authorId") long authorId) throws IOException {
        LOGGER.info("Call save book action");

        Author author = authorHandler.findOne(authorId);

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

//        User owner = userHandler.findByEmail("j.thoulouse@gmail.com");

        Book book = new Book.BookBuilder(title).withCollection(collection).withShelf(shelf).withAuthor(author).withOwner(user).build();

        Book result = bookHandler.save(book);

        ObjectMapper mapper = new ObjectMapper();

        return Response.ok(mapper.writeValueAsString(result)).build();
    }

    /**
     * Ajout d'un bouquin
     */
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Context HttpServletRequest request, @FormParam("id") long bookId, @FormParam("title") String title, @FormParam("collection") String collection, @FormParam("shelf") String shelf, @FormParam("authorId") long authorId) throws IOException {
        Author author = authorHandler.findOne(authorId);

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

//        User owner = userHandler.findByEmail("j.thoulouse@gmail.com");

        Book book = new Book.BookBuilder(title).withCollection(collection).withShelf(shelf).withAuthor(author).withOwner(user).withId(bookId).build();

        bookHandler.update(book);

        ObjectMapper mapper = new ObjectMapper();

        return Response.ok(mapper.writeValueAsString(book)).build();
    }

}
