package router;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class SubRouter extends AbstractVerticle {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new SubRouter());
    }

    @Override
    public void start() {

        Router mainRouter = Router.router(vertx);

        Router foodRouter = Router.router(vertx);

        Router stationeryRouter = Router.router(vertx);

        mainRouter.route(HttpMethod.GET, "/food/*").subRouter(foodRouter);

        mainRouter.route(HttpMethod.GET, "/stationery/*").subRouter(stationeryRouter);

        foodRouter.route("/vegetables").handler(context -> {

            context.response().putHeader("content-type", "text/plain").setChunked(true);

            context.response().write("Vegetables : { Potato, Tomato, Onion }");

            context.end();
        });

        foodRouter.route("/fruits").handler(context -> {

            context.response().putHeader("content-type", "text/plain").setChunked(true);

            context.response().write("Fruits : { Apple, Mango, Banana }");

            context.end();
        });

        stationeryRouter.route("/books").handler(context -> {

            context.response().putHeader("content-type", "text/plain").setChunked(true);

            context.response().write("Books : { Veerangana Rani Laxmibai , Chattrapati Shivaji Maharaj, Maharana Pratap }");

            context.end();
        });

        stationeryRouter.route("/pencils").handler(context -> {

            context.response().putHeader("content-type", "text/plain").setChunked(true);

            context.response().write("Pencils : { Apsara Dark , Nataraj , Doms }");

            context.end();
        });

        vertx.createHttpServer().requestHandler(mainRouter).listen(8080).onSuccess(ok -> System.out.println("Server listening on PORT 8080"));

    }
}
