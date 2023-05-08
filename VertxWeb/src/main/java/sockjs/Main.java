package sockjs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Main extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Main());
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        SockJSHandlerOptions options = new SockJSHandlerOptions()
                .setHeartbeatInterval(2000);

        SockJSHandler sockJSHandler = SockJSHandler.create(vertx, options);

        router.route("/socket/*")
                .subRouter(sockJSHandler.socketHandler(sockJSSocket -> {

                    sockJSSocket.write("Hello from server ");

                    sockJSSocket.handler(buffer -> System.out.println(buffer.toString()));


                }));

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(SocketAddress.inetSocketAddress(new InetSocketAddress(InetAddress.getByName("localhost"), 8080)))
                .onComplete(result -> {
                    if (result.succeeded()) {
                        System.out.println("Server listening at port 8080");
                    } else {
                        result.cause().printStackTrace();
                    }
                });
    }
}
