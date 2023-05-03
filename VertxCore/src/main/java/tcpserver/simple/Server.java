package tcpserver.simple;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Server());
    }

    @Override
    public void start() {
        NetServer server = vertx.createNetServer();

        server.connectHandler(socket -> {

            System.out.println(socket.remoteAddress() + " connected");

            socket.write("Hello from Server");

        });

        server.listen(9999, "localhost").onSuccess(ok -> System.out.println("Server listening on port 9999"));

    }
}
