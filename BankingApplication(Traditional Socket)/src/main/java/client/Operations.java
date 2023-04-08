package client;

import utility.Const;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Operations
{
    public void deposit(DataInputStream reader, DataOutputStream writer, float amount, String customerId)
    {
        try
        {

            writer.writeUTF("deposit amount");

            writer.flush();

            writer.writeUTF(customerId + ":" + amount);

            writer.flush();

            if (reader.readUTF().equalsIgnoreCase("deposit success"))
            {
                System.out.println(Const.NEW_LINE_SEPARATOR + "Deposit of " + amount + " successful");
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void withdraw(DataInputStream reader, DataOutputStream writer, double amount, String customerId)
    {
        try
        {
            writer.writeUTF("withdraw amount");

            writer.flush();

            writer.writeUTF(customerId + ":" + amount);

            writer.flush();

            if (reader.readUTF().equalsIgnoreCase("success"))
            {
                System.out.println(Const.NEW_LINE_SEPARATOR + "Withdrawal of " + amount + " successful");
            }

            else
            {
                System.out.println(Const.NEW_LINE_SEPARATOR + Const.RED_COLOUR + "Invalid withdraw amount" + Const.RESET_COLOUR);
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void checkBalance(DataInputStream reader, DataOutputStream writer, String customerId)
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
                System.out.println(Const.RED_COLOUR + Const.NEW_LINE_SEPARATOR + "server balance check error" + Const.RESET_COLOUR);
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

}
