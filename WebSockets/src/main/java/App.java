import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class App
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);

        WebSocketHandler handler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {

                factory.register(MyWebSocketEndpoint.class);
            }
        };


        server.setHandler(handler);

        server.setStopTimeout(400000);

        server.start();

        server.join();
    }
}
