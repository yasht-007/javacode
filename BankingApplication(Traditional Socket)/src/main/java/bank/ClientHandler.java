package bank;


import api.AccountHolder;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;


public class ClientHandler implements Runnable
{
    private static DataInputStream reader = null;
    private static DataOutputStream writer = null;

    private static final HashMap<String,AccountHolder> accounts = AccountDb.getAccounts();
    private static Socket socket;

    public ClientHandler(Socket socket)
    {
        ClientHandler.socket = socket;
    }

    public void handleClient()
    {
        try
        {
            reader = new DataInputStream(socket.getInputStream());

            writer = new DataOutputStream(socket.getOutputStream());

            while (true)
            {

                String operation = reader.readUTF().toLowerCase().trim();

                switch (operation)
                {
                    case "login" ->
                    {
                        writer.writeUTF("ok");

                        writer.flush();

                        new LoginHandler().handleLogin();

                    }

                    case "open account" -> new OpenAccountHandler().handleOpenAccount();

                case "deposit amount" ->
                {
                    writer.writeUTF("ok");

                    writer.flush();

                    String[] data = reader.readUTF().split(":");

                    AccountHolder accountDetails = accounts.get(data[0]);

                    new DepositHandler(accountDetails, Float.parseFloat(data[1])).handleDeposit();
                }

                case "withdraw amount" ->
                {
                    writer.writeUTF("ok");

                    writer.flush();

                    String[] data = reader.readUTF().split(":");

                    AccountHolder accountDetails = accounts.get(data[0]);

                    new WithdrawHandler(accountDetails, Float.parseFloat(data[1])).handleWithdraw();

                }

                case "check balance" ->
                {
                    writer.writeUTF("ok");

                    writer.flush();

                    String customerID = reader.readUTF();

                    AccountHolder accountdetails = accounts.get(customerID);

                    new CheckBalanceHandler(accountdetails).handleBalanceCheck();
                }

                    default -> System.out.println("Error case client handler");
                }
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }


    @Override
    public void run()
    {
        handleClient();
    }

    public static DataInputStream getReader()
    {
        return reader;
    }

    public static DataOutputStream getWriter()
    {
        return writer;
    }

}
