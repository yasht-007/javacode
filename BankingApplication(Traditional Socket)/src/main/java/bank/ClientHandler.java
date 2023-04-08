package bank;


import api.AccountHolder;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;


public class ClientHandler implements Runnable
{
    private static final HashMap<String, AccountHolder> accounts = AccountDb.getAccounts();
    private final Socket socket;

    public ClientHandler(Socket socket)
    {
        this.socket = socket;
    }

    public void handleClient()
    {
        try
        {
            DataInputStream reader = new DataInputStream(socket.getInputStream());

            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

            String ip = socket.getInetAddress().getHostAddress();

            while (socket.isConnected())
            {

                String operation = reader.readUTF().toLowerCase().trim();

                switch (operation)
                {
                    case "login" -> new LoginHandler(reader, writer,ip).handleLogin();

                    case "open account" -> new OpenAccountHandler(reader, writer,ip).handleOpenAccount();

                    case "deposit amount" ->
                    {
                        String[] data = reader.readUTF().split(":");

                        AccountHolder accountDetails = accounts.get(data[0]);

                        new DepositHandler(writer, accountDetails, Float.parseFloat(data[1]),ip).handleDeposit();
                    }

                    case "withdraw amount" ->
                    {

                        String[] data = reader.readUTF().split(":");

                        AccountHolder accountDetails = accounts.get(data[0]);

                        new WithdrawHandler(writer, accountDetails, Float.parseFloat(data[1]),ip).handleWithdraw();

                    }

                    case "check balance" ->
                    {
                        writer.writeUTF("ok");

                        writer.flush();

                        String customerID = reader.readUTF();

                        AccountHolder accountdetails = accounts.get(customerID);

                        new CheckBalanceHandler(writer, accountdetails,ip).handleBalanceCheck();
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

}
