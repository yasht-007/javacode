package client;

import bank.ClientHandler;
import utility.Const;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Operations
{
    private static final DataInputStream reader = Bootstrap.getReader();
    private static final DataOutputStream writer = Bootstrap.getWriter();

    public static void deposit(float amount, String customerId)
    {
        try
        {

            writer.writeUTF("deposit amount");

            writer.flush();

            if (reader.readUTF().equalsIgnoreCase("ok"))
            {
                writer.writeUTF(customerId + ":" + amount);

                writer.flush();

                if (reader.readUTF().equalsIgnoreCase("deposit success"))
                {
                    System.out.println(Const.NEW_LINE_SEPARATOR + "Deposit of " + amount + " successful");
                }
            }

            else
            {
                System.err.println("Server deposit error");
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void withdraw(double amount, String customerId)
    {
        try
        {
            writer.writeUTF("withdraw amount");

            writer.flush();

            if (reader.readUTF().equalsIgnoreCase("ok"))
            {
                writer.writeUTF(customerId + ":" + amount);

                writer.flush();

                if (reader.readUTF().equalsIgnoreCase("success"))
                {
                    System.out.println(Const.NEW_LINE_SEPARATOR + "Withdrawal of " + amount + " successful");
                }

                else
                {
                    System.err.println(Const.NEW_LINE_SEPARATOR + "Invalid withdraw amount");
                }
            }

            else
            {
                System.err.println(Const.NEW_LINE_SEPARATOR + "server withdraw error");
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void checkBalance(String customerId)
    {
        try
        {

            writer.writeUTF("check balance");

            writer.flush();

            if (reader.readUTF().equalsIgnoreCase("ok"))
            {

                writer.writeUTF(customerId);

                writer.flush();

                System.out.println(Const.NEW_LINE_SEPARATOR + "Current Balance : " + reader.readUTF());

            }

            else
            {
                System.err.println(Const.NEW_LINE_SEPARATOR + "server balance check error");
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

}
