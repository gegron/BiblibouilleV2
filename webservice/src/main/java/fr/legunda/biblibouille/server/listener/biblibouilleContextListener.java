package fr.legunda.biblibouille.server.listener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import fr.legunda.biblibouille.server.module.WebModule;

public class BiblibouilleContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new WebModule());
    }

}
