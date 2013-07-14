package fr.legunda.biblibouille.server.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.biblibouille.model.User;
import fr.biblibouille.model.handlers.UserHandler;
import org.apache.commons.httpclient.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.logging.Logger;

@Path("/security")
@Singleton
public class SecurityResource extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private UserHandler userHandler;

    @Inject
    public SecurityResource(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @POST
    @Path("/login")
    public void login(@Context HttpServletRequest httpServletRequest, @Context HttpServletResponse httpServletResponse, @FormParam("email") String email, @FormParam("password") String password) throws ServletException, IOException {
//        String email = httpServletRequest.getParameter("email");
//        String password = httpServletRequest.getParameter("password");

        User user = userHandler.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = httpServletRequest.getSession(true);

            session.setAttribute("user", user);

            httpServletResponse.sendRedirect("/index.html");
        }
        else {
            httpServletResponse.sendError(HttpStatus.SC_FORBIDDEN);
        }
    }

    @GET
    @Path("/logout")
    public void login(@Context HttpServletRequest httpServletRequest, @Context HttpServletResponse httpServletResponse) throws ServletException, IOException {
        HttpSession session = httpServletRequest.getSession(false);

        session.invalidate();

        httpServletResponse.sendRedirect("/index.html");
    }

}
