package bank;


import utility.Const;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Bootstrap
{

    public static void main(String[] args)
    {

        try (ServerSocket serverSocket = new ServerSocket(9999))
        {
            System.out.println("Server started at port 9999");

            ExecutorService executor = Executors.newFixedThreadPool(Const.NO_OF_THREADS);

            while (!Thread.currentThread().isInterrupted())
            {
                Socket socket = serverSocket.accept();

                System.out.println("client " + socket.getRemoteSocketAddress() + " connected");

                int activeThreadCount = ((ThreadPoolExecutor) executor).getActiveCount();

                DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

                DataInputStream reader = new DataInputStream(socket.getInputStream());

                if (activeThreadCount < Const.NO_OF_THREADS)
                {
                    writer.writeUTF("0");

                    writer.flush();

                    executor.submit(new ClientHandler(socket, reader, writer));
                }

                else
                {
                    writer.writeUTF("-1");

                    writer.flush();

                    System.err.println("client " + socket.getRemoteSocketAddress().toString() + " disconnected due to server load");

                }

            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
