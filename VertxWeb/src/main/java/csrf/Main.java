package csrf;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import java.util.UUID;

public class Main extends AbstractVerticle {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Main());
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        SessionHandler sessionHandler = SessionHandler.create(LocalSessionStore.create(vertx));

        router.route().handler(sessionHandler);

        router.post("/login").handler(context -> {

            Session session = context.session();

            String csrfToken = UUID.randomUUID().toString();

            session.put("csrfToken", csrfToken);

            Cookie cookie = Cookie.cookie("XSRFTOKEN", csrfToken);

            context.response().addCookie(cookie);

            context.response().end();

        });

        router.post("/protected-data").handler(context -> {

            String xsrfToken = context.request().getHeader("X-XSRF-TOKEN");

            Session session = context.session();

            String sessionToken = session.get("csrfToken");

            context.response().setChunked(true);

            if (sessionToken.equals(xsrfToken)) {

                context.response().write("Token Valid");

            } else {

                context.response().write("Token InValid");

            }

            context.response().end();
        });

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080)
                .onSuccess(ok -> System.out.println("Server listening on Port 8080"));

    }
}
