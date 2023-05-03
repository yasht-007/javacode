package cookie_session;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class SessionMain extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(SessionMain.class.getName(), new DeploymentOptions().setInstances(3));

    }

    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx, "session")).setCookieless(true).setSessionTimeout(9000));

        router.post("/session").produces("application/json").handler(context -> {

            Session session = context.session();

            System.out.println(session.timeout());

            session.put("id", "yash007");

            session.put("password", "yash1234");

            context.response().end(new JsonObject()
                    .put("id", session.get("id"))
                    .put("password", session.get("password"))
                    .encodePrettily());

        });

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080).onSuccess(ok -> System.out.println("Server listening at Port 8080"));

    }
}
