package Router;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class GetRequest extends AbstractVerticle {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(GetRequest.class.getName(), new DeploymentOptions().setInstances(5));

    }

    @Override
    public void start(Promise<Void> startPromise) {

        Router router = Router.router(vertx);

        /*Path = "/home" */
        Route route = router.route(HttpMethod.GET, "/home");

        route.handler(context -> {

            System.out.println("Request accepted by " + Thread.currentThread().getName());

            HttpServerResponse response = context.response();

            response.putHeader("content-type", "text/plain");

            response.setChunked(true);

            response.write("hello from yash!\n");

            context.next();

        });

        route.handler(context -> {

            HttpServerResponse response = context.response();

            response.write("I am fine\n");

            context.next();
        });

        route.handler(context -> {

            HttpServerResponse response = context.response();

            response.write("How are you?\n");

            context.response().end();
        });

        /*Path ="/welcome"*/

        router.route(HttpMethod.GET, "/welcome").handler(context -> {

            System.out.println("Request accepted by " + Thread.currentThread().getName());

            HttpServerResponse response = context.response();

            response.putHeader("content-type", "text/plain").setChunked(true);

            response.write("Welcome to Motadata!");

            response.end();

        });

        router.get("/json").respond(ctx -> Future.succeededFuture("[\n" + "  {\n" + "    \"id\": 10,\n" + "    \"name\": \"yash\"\n" + "  },\n" + "  \n" + "   {\n" + "    \"id\": 20,\n" + "    \"name\": \"ayush\"\n" + "  }\n" + "]"));

        router.get("/fruits").respond(ctx -> ctx.response().setChunked(true).write("Apple\nMango\nBanana"));


        HttpServer server = vertx.createHttpServer();

        server.requestHandler(router).listen(8080).onSuccess(ok -> System.out.println("Server listening at Port 8080"));
    }
}
