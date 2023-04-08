package bank;


import api.AccountHolder;
import utility.Const;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;


public class ClientHandler implements Runnable
{
    private static final HashMap<String, AccountHolder> accounts = AccountDb.getAccounts();
    private final Socket socket;
    private final DataInputStream reader;
    private final DataOutputStream writer;

    public ClientHandler(Socket socket, DataInputStream reader, DataOutputStream writer)
    {
        this.socket = socket;

        this.reader = reader;

        this.writer = writer;
    }


    @Override
    public void run()
    {
        handleClient();
    }

    public void handleClient()
    {
        try
        {
            String ip = socket.getInetAddress().getHostAddress();

            while (socket.isConnected())
            {

                String operation = reader.readUTF().toLowerCase().trim();

                switch (operation)
                {
                    case "login" -> new LoginHandler(reader, writer, ip).handleLogin();

                    case "open account" -> new OpenAccountHandler(reader, writer, ip).handleOpenAccount();

                    case "deposit amount" ->
                    {
                        String[] data = reader.readUTF().split(":");

                        AccountHolder accountDetails = accounts.get(data[0]);

                        new DepositHandler(writer, accountDetails, Float.parseFloat(data[1]), ip).handleDeposit();
                    }

                    case "withdraw amount" ->
                    {

                        String[] data = reader.readUTF().split(":");

                        AccountHolder accountDetails = accounts.get(data[0]);

                        new WithdrawHandler(writer, accountDetails, Float.parseFloat(data[1]), ip).handleWithdraw();

                    }

                    case "check balance" ->
                    {
                        writer.writeUTF("ok");

                        writer.flush();

                        String customerID = reader.readUTF();

                        AccountHolder accountdetails = accounts.get(customerID);

                        new CheckBalanceHandler(writer, accountdetails, ip).handleBalanceCheck();
                    }

                    default -> System.out.println("Error case client handler");
                }
            }
        }
        catch (Exception exception)
        {
            if (exception instanceof EOFException)
            {
                System.out.println(Const.RED_COLOUR + "client " + socket.getRemoteSocketAddress().toString() + " disconnected" + Const.RESET_COLOUR);
            }
            else
            {
                exception.printStackTrace();
            }
        }

    }

}
