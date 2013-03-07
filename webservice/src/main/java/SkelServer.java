import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;

import java.net.UnknownHostException;

public class SkelServer {

    //private static final Logger LOGGER = LoggerFactory.getLogger(SkelServer.class);

    /**
     * Container
     */
    //private final Datasource ds;
    //private final UserPersistence up;

    private final Vertx vertx = Vertx.newVertx();

    public SkelServer() throws UnknownHostException {
//        ds = new Datasource();
  //      up = new UserPersistence(ds.mongo);
    }

    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();
        RouteMatcher routeMatcher = getRouteMatcher();
        server.requestHandler(routeMatcher).listen(8080, "localhost");
    }

    private RouteMatcher getRouteMatcher() {
        RouteMatcher routeMatcher = new RouteMatcher();
        routeMatcher.get("/users", new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest req) {
                Buffer buff = new Buffer();
//                for (DBObject user : up.listAll()) {
//                    buff.appendString(user.toString());
//                }
                req.response.end(buff);
            }
        });
        routeMatcher.get("/user/:id", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                int userId = Integer.parseInt(req.params().get("id"));
                String content = req.params().get("content");
                req.response.end("my users: " + userId + " content: " + content);
            }
        });
        return routeMatcher;
    }

    public static void main(String[] args) throws Exception {

        Vertx vertx = Vertx.newVertx();

        HttpServer server = vertx.createHttpServer();

        server.requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest request) {
                //log.info("A request has arrived on the server!");
                System.out.println("Requête reçue");
            }
        }).listen(8080, "localhost");

    }
}
