package bank;

import api.AccountHolder;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.HashMap;

public class Bootstrap
{
    private static final HashMap<String, AccountHolder> accounts = new HashMap<>();

    public static void main(String[] args)
    {

        try (ZContext context = new ZContext(2); ZMQ.Socket socket = context.createSocket(SocketType.PAIR))
        {

            if (socket.bind("tcp://*:9999"))
            {
                System.out.println("Bank Server successfully bound");

            }
            else
            {
                System.err.println("Bind unsuccessful");

                System.exit(0);
            }

            while (!Thread.currentThread().isInterrupted())
            {
                String operation = socket.recvStr(0).toLowerCase().trim();

                switch (operation)
                {
                    case "login" ->
                    {
                        socket.send("ok", 0);

                        new LoginHandler(socket, accounts).handleLogin();
                    }

                    case "open account" -> new OpenAccountHandler(socket, accounts).handleOpenAccount();

                    case "deposit amount" ->
                    {
                        socket.send("ok", 0);

                        String[] data = socket.recvStr(0).split(":");

                        AccountHolder accountDetails = accounts.get(data[0]);

                        new DepositHandler(socket, accountDetails, Float.parseFloat(data[1])).handleDeposit();
                    }

                    case "withdraw amount" ->
                    {
                        socket.send("ok", 0);

                        String[] data = socket.recvStr().split(":");

                        AccountHolder accountDetails = accounts.get(data[0]);

                        new WithdrawHandler(socket, accountDetails, Float.parseFloat(data[1])).handleWithdraw();

                    }

                    case "check balance" ->
                    {
                        socket.send("ok", 0);

                        String customerID = socket.recvStr(0);

                        AccountHolder accountdetails = accounts.get(customerID);

                        new CheckBalanceHandler(socket, accountdetails).handleBalanceCheck();
                    }

                    default ->
                    {

                    }
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
