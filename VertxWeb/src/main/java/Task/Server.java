package Task;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Server()).onSuccess(ok -> vertx.deployVerticle(new Client()));
    }

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> request.body().onSuccess(buffer -> vertx.fileSystem().writeFile("/home/yash/taskout.txt", buffer).onComplete(handler -> {

            if (handler.succeeded()) {

                System.out.println("File received and wrote successfully");

            } else {

                handler.cause().printStackTrace();
            }
        }))).listen(8080);
    }
}
