import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;

@WebSocket(maxIdleTime=30000)

public class MyWebSocketEndpoint {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket opened: " + session.getRemoteAddress());

        sendMessage(session,"Hu Server chu");

    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("WebSocket closed: " + statusCode + " - " + reason);
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println("WebSocket message received: " + message);
    }

    @OnWebSocketError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    public static void sendMessage(Session session,String message) {
        try {
            session.getRemote().sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}