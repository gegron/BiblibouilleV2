package fr.legunda.biblibouille.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import fr.biblibouille.model.Author;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class BiblibouilleServer {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new HttpHandler() {

            @Override
            public void handle(HttpExchange httpExchange) throws IOException {

//                String nom = new String("NORGE".getBytes(), Charset.forName("UTF-8"));
//                String prenom = new String("Gérôme".getBytes(), Charset.forName("UTF-8"));


//                Author auteur = new Author("EGRON", "Gérôme");

                List<Author> authors = Author.findAll();

                ObjectMapper mapper = new ObjectMapper();



                ByteArrayOutputStream os = new ByteArrayOutputStream();

                //mapper.writeValue();


//                mapper.writeValue(os, auteur);
                byte[] response = mapper.writeValueAsBytes(authors.get(0));
//
                httpExchange.sendResponseHeaders(200, response.length);
                httpExchange.getResponseBody().write(response);
                httpExchange.close();
            }
        });

        server.start();

    }

}
