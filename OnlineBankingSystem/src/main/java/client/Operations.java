package client;

import org.zeromq.ZMQ;

import utility.Const;

public class Operations
{
    private static final ZMQ.Socket socket = Bootstrap.getSocket();

    public static void deposit(float amount, String customerId)
    {
        socket.send("deposit amount", 0);

        if (socket.recvStr(0).equalsIgnoreCase("ok"))
        {
            socket.send(customerId + ":" + amount, 0);

            if (socket.recvStr(0).equalsIgnoreCase("deposit success"))
            {
                System.out.println(Const.NEW_LINE_SEPARATOR + "Deposit of " + amount + " successful");
            }
        }

        else
        {
            System.err.println("Server deposit error");
        }
    }

    public static void withdraw(double amount, String customerId)
    {
        socket.send("withdraw amount", 0);

        if (socket.recvStr(0).equalsIgnoreCase("ok"))
        {
            socket.send(customerId + ":" + amount);

            if (socket.recvStr(0).equalsIgnoreCase("success"))
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

    public static void checkBalance(String customerId)
    {
        socket.send("check balance");

        if (socket.recvStr(0).equalsIgnoreCase("ok"))
        {

            socket.send(customerId);

            System.out.println(Const.NEW_LINE_SEPARATOR + "Current Balance : " + socket.recvStr(0));

        }

        else
        {
            System.err.println(Const.NEW_LINE_SEPARATOR + "server balance check error");
        }

    }

}
