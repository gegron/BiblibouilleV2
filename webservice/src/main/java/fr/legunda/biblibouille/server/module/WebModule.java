package fr.legunda.biblibouille.server.module;

import com.google.inject.Scopes;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import fr.biblibouille.model.module.PersistenceModule;
import fr.legunda.biblibouille.server.resources.AuthorResource;
import fr.legunda.biblibouille.server.resources.BookResource;
import fr.legunda.biblibouille.server.resources.HelloWorldRessource;

public class WebModule extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        install(new PersistenceModule());

        bind(AuthorResource.class).in(Scopes.SINGLETON);
        bind(BookResource.class).in(Scopes.SINGLETON);
        bind(HelloWorldRessource.class).in(Scopes.SINGLETON);

        serve("/resource/*").with(GuiceContainer.class);
    }

}
