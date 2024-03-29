package client;

import utility.Const;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Bootstrap
{

    public static void main(String[] args)
    {
        Socket socket = new Socket();

        DataInputStream socketinputreader = null;

        DataOutputStream writer = null;

        BufferedReader reader = null;

        try
        {

            socket.connect(new InetSocketAddress(InetAddress.getByName("localhost"), 9999));

            socketinputreader = new DataInputStream(socket.getInputStream());

            writer = new DataOutputStream(socket.getOutputStream());

            reader = new BufferedReader(new InputStreamReader(System.in));

            if (socketinputreader.readUTF().equalsIgnoreCase("-1"))
            {
                System.out.println(Const.RED_COLOUR + "Bank server is down. Please try again after sometime" + Const.RESET_COLOUR);

                Thread.currentThread().interrupt();
            }

            while (!Thread.currentThread().isInterrupted())
            {

                displayDashboard();

                System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your choice here : ");

                String option = reader.readLine();

                switch (option)
                {
                    case "1" -> new LoginHandler(socketinputreader, writer, reader).handleLogin();

                    case "2" -> new OpenAccountHandler(socketinputreader, writer, reader).handleOpenAccount();

                    case "3" -> Thread.currentThread().interrupt();

                    default -> System.out.println(Const.RED_COLOUR + "Please enter valid choice" + Const.RESET_COLOUR);

                }

            }

        }
        catch (Exception exception)
        {
            if (exception instanceof EOFException)
            {
                System.out.println(Const.RED_COLOUR + "sever connection closed" + Const.RESET_COLOUR);
            }

            else
            {
                exception.printStackTrace();
            }
        }

        finally
        {
            try
            {
                socket.close();

                if (socketinputreader != null)
                {
                    socketinputreader.close();
                }

                if (writer != null)
                {
                    writer.close();
                }

                if (reader != null)
                {
                    reader.close();
                }
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public static void displayDashboard()
    {
        try
        {

            System.out.println(utility.Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Welcome to Online Banking : " + utility.Const.NEW_LINE_SEPARATOR);

            System.out.println("1. Login");

            System.out.println("2. Open Account");

            System.out.println("3. Exit");

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    public static void displayServices()
    {
        try
        {

            System.out.println(Const.NEW_LINE_SEPARATOR + "Welcome to Online Banking Facility" + Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Please enter select which operation you want to perform : " + Const.NEW_LINE_SEPARATOR);

            System.out.println("1.Deposit amount" + Const.NEW_LINE_SEPARATOR);

            System.out.println("2.Withdraw amount" + Const.NEW_LINE_SEPARATOR);

            System.out.println("3.Check balance" + Const.NEW_LINE_SEPARATOR);

            System.out.println("4.Log out" + Const.NEW_LINE_SEPARATOR);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

}
