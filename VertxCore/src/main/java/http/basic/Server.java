package http.basic;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;

public class Server extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Server());
    }

    @Override
    public void start() throws Exception {

        HttpServer server= vertx.createHttpServer();

        server.requestHandler(req -> {
            System.out.println(req.remoteAddress());
            req.response().putHeader("content-type", "text/html").end("<html><body><h1>Hello from vert.x!</h1></body></html>");
        });

        server.listen(8080);


//        HttpServer server = vertx.createHttpServer();
//
//        server.requestHandler(request -> {
//
//            System.out.println(request.remoteAddress());
//
//            String str = "Hello from Server";
//
//            request.response().putHeader("Content-Length", Integer.toString(str.length()*2));
//
//            request.response().write(str);
//
////            request.end();
//
//            request.response().write(str);
//
//        }).listen(8080).onSuccess(ok-> System.out.println("Server listening at port 8080"));
    }
}
