package fr.legunda.biblibouille.server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created with IntelliJ IDEA.
 * User: Gerome
 * Date: 07/03/13
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */
@Path("/helloworld")
public class HelloWorldRessource {

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello world";
    }

}
