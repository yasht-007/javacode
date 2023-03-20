package ChatApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadMessage extends Thread
{
    Socket socket;
    BufferedReader reader;
    String user;

    ReadMessage(Socket socket, String user)
    {
        this.socket = socket;

        this.user = user;
    }

    @Override
    public void run()
    {
        startReading();
    }

    void startReading()
    {
        System.out.println();

        System.out.println(user + " reading mode activated" + Const.NEW_LINE_SEPERATOR);

        try
        {
            if (!socket.isClosed())
            {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }

            else
            {
                System.out.println("Socket is closed");
            }

            while (!Const.END_CONNECTION)
            {

                String message;

                if ((message = reader.readLine()) != null)
                {

                    if (message.equals("bye"))
                    {
                        System.out.println(user + " terminated the chat");

                        Const.END_CONNECTION = true;

                        socket.close();

                        System.exit(0);

                    }

                    System.out.print(user.equals("Client") ? Const.ANSI_BLUE + Const.SERVER : Const.ANSI_YELLOW + Const.CLIENT);

                    System.out.println(": " + message);
                }

            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

}
