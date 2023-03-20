package ChatApplication;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteMessage extends Thread
{
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;
    String user;

    public WriteMessage(Socket socket, String user)
    {
        this.socket = socket;

        this.user = user;
    }

    @Override
    public void run()
    {
        startWriting();
    }

    void startWriting()
    {
        System.out.println(user + " writing mode enabled" + Const.NEW_LINE_SEPERATOR);

        try
        {
            if (!socket.isClosed())
            {
                writer = new PrintWriter(socket.getOutputStream());

            }

            else
            {
                System.out.println("Socket is closed");
            }

            reader = new BufferedReader(new InputStreamReader(System.in));

            while (!Const.END_CONNECTION)
            {
                String input = reader.readLine();

                if (input.trim().equalsIgnoreCase("bye"))
                {
                    Const.END_CONNECTION = true;

                }

                writer.println(input);

                writer.flush();

                if (Const.END_CONNECTION)
                {
                    socket.close();

                    System.exit(0);
                }

            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
