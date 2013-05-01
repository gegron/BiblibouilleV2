package fr.legunda.biblibouille.server.resources;

import fr.biblibouille.model.Author;
import fr.biblibouille.model.handlers.AuthorHandler;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/author")
public class AuthorResource extends HttpServlet {

    private final static ObjectMapper mapper = new ObjectMapper();

    private AuthorHandler authorHandler = new AuthorHandler();

    /**
     * Liste de tous les auteurs
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String findAll() throws IOException {
        return mapper.writeValueAsString(authorHandler.findAll());
    }

    /**
     * Author selon id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findOne(@PathParam("id") Long id) throws IOException {
        return mapper.writeValueAsString(authorHandler.findOne(id));
    }

    /**
     * Ajout d'un auteur
     */
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("firstname") String firstname, @FormParam("lastname") String lastname) throws IOException {
        Author author = new Author.AuthorBuilder().withFirstname(firstname).withLastname(lastname).build();

        Author authorResult = authorHandler.save(author);

        return Response.ok(mapper.writeValueAsString(authorResult)).build();
    }

}
