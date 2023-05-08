package staticexample;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.FileSystemAccess;
import io.vertx.ext.web.handler.StaticHandler;

public class Main extends AbstractVerticle {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(Main.class.getName());
    }

    @Override
    public void start() throws Exception {

        Router router = Router.router(vertx);

        router.route(HttpMethod.GET, "/static/*").handler(StaticHandler.create(FileSystemAccess.ROOT,"/root/IdeaProjects/VertxWeb/src/main/java/staticexample/webroot"));

        vertx.createHttpServer().requestHandler(router).listen(8080).onSuccess(ok -> System.out.println("Server listening at port 8080"));
    }
}
