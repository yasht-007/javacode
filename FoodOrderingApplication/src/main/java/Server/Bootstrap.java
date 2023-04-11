package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class Bootstrap
{

    public static void main(String[] args)
    {

        try (ServerSocket serverSocket = new ServerSocket(9999);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("Server started at port 9999");

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
