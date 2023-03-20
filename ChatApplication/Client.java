package ChatApplication;

import java.net.Socket;

public class Client
{
    public static void main(String[] args)
    {
        Socket socket = null;

        ReadMessage readMessage;

        WriteMessage writeMessage;

        try
        {
            socket = new Socket(Const.HOST, Const.PORT_NO);

            System.out.println("Connection successfully established...");

            readMessage = new ReadMessage(socket, Const.CLIENT);

            writeMessage = new WriteMessage(socket, Const.CLIENT);

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

                if (socket != null) socket.close();

            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}

