package bank;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Bootstrap
{

    public static void main(String[] args)
    {

        try (ServerSocket serverSocket = new ServerSocket(9999);)
        {
            System.out.println("Server started at port 9999");

            ExecutorService executor = Executors.newCachedThreadPool();

            while (!Thread.currentThread().isInterrupted())
            {
                Socket socket = serverSocket.accept();

                executor.submit(new ClientHandler(socket));

            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
