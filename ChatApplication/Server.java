package ChatApplication;

import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args)
    {
        System.out.println("Server is going to start...");

        ServerSocket serverSocket = null;

        Socket socket = null;

        ReadMessage readMessage;

        WriteMessage writeMessage;

        try
        {
            serverSocket = new ServerSocket(Const.PORT_NO);

            System.out.println("Server is ready to accept connection");

            System.out.println("Waiting for client...");

            socket = serverSocket.accept();

            System.out.println("Client connected");

            readMessage = new ReadMessage(socket, Const.SERVER);

            writeMessage = new WriteMessage(socket, Const.SERVER);

            readMessage.start();

            writeMessage.start();

            readMessage.join();

            writeMessage.join();

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            try
            {

                if (serverSocket != null)
                    serverSocket.close();

                if (socket != null)
                    socket.close();

            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
