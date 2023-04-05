package client;

import org.zeromq.ZMQ;

import java.io.BufferedReader;

import java.io.InputStreamReader;

public class Operations
{
    private static final ZMQ.Socket socket = Bootstrap.getSocket();
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void deposit(double amount, String customerId)
    {
        socket.send("deposit");

        if (socket.recvStr().equalsIgnoreCase("ok"))
        {
            socket.send(customerId + ":" + amount);

            if (socket.recvStr().equalsIgnoreCase("success"))
            {
                System.out.println("Deposit of " + amount + " successful");
            }
        }

        else
        {
            System.err.println("Deposit of " + amount + " successful");
        }
    }

    public static void withdraw(double amount, String customerId)
    {
        socket.send("withdraw");

        if (socket.recvStr().equalsIgnoreCase("ok"))
        {
            socket.send(customerId + ":" + amount);

            if (socket.recvStr().equalsIgnoreCase("success"))
            {
                System.out.println("Withdrawal of " + amount + " successful");
            }

            else
            {
                System.err.println("Invalid withdraw amount");
            }
        }

        else
        {
            System.err.println("server withdraw error");
        }
    }

    public static void checkBalance(String customerId)
    {
        socket.send("check balance");

        socket.send(customerId);

        System.out.println("Current Balance : " + socket.recvStr());

    }

}
