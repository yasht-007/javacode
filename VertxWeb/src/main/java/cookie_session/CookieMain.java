package cookie_session;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.Router;

public class CookieMain extends AbstractVerticle {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(CookieMain.class.getName(), new DeploymentOptions().setInstances(3));
    }

    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.post("/books").handler(context -> {

            Cookie cookie = context.request().getCookie("year");

            System.out.println(cookie.getValue());

            context.response().addCookie(Cookie.cookie("request", "completed").setHttpOnly(true).setMaxAge(60));

            context.response().end();

        });

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080).onSuccess(ok -> System.out.println("Server listening at Port 8080"));

    }
}
