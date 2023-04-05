package bank;

import api.AccountHolder;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.HashMap;

public class Bootstrap
{
    private static ZContext context = new ZContext();

    private static ZMQ.Socket socket = context.createSocket(SocketType.PAIR);

    public static ZMQ.Socket getSocket()
    {
        return socket;
    }

    private static HashMap<String, AccountHolder> accounts = new HashMap<>();
    public static int LAST_ASSIGNED_CUSTOMER_ID = 1000;
    public static int LAST_ASSIGNED_ACCOUNT_NO = 4000000;

    public static boolean isAccountHolder(String customerId)
    {
        return accounts.containsKey(customerId);
    }

    public static boolean verifyPassword(String customerId, String password)
    {
        if (accounts.get(customerId).getPassword().equalsIgnoreCase(password))
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    public static void main(String[] args)
    {

        try
        {

            if (socket.bind("tcp://*:9999"))
            {
                System.out.println("Bind successful");
            }

            else
            {
                System.out.println("Bind unsuccessful");

                System.exit(0);
            }

            while (true)
            {
                String operation = socket.recvStr();

                if (operation.equalsIgnoreCase("login"))
                {
                    socket.send("ok");

                    String[] service = socket.recvStr().split(":");

                    if (isAccountHolder(service[0]))
                    {
                        boolean status = verifyPassword(service[0], service[1]);

                        if (status)
                        {
                            socket.send("valid account holder");
                        }

                        else
                        {
                            socket.send("invalid password");
                        }
                    }

                    else
                    {
                        socket.send("account not found");
                    }

                }

                if (operation.equalsIgnoreCase("open account"))
                {
                    String[] accountDetails = socket.recvStr().split("\n");

                    String customerId = String.valueOf(LAST_ASSIGNED_CUSTOMER_ID++);

                    String accountNo = String.valueOf(LAST_ASSIGNED_ACCOUNT_NO++);

                    AccountHolder accountHolder = new AccountHolder(customerId, accountNo, accountDetails[0], accountDetails[1], accountDetails[2], accountDetails[3], accountDetails[4], accountDetails[5]);

                    accounts.put(customerId, accountHolder);

                    socket.send("success " + customerId + " " + accountNo);

                }

                if (operation.equalsIgnoreCase("deposit"))
                {
                    socket.send("ok");

                    String[] data = socket.recvStr().split(":");

                    socket.send(Operations.deposit(accounts.get(data[0]), Float.parseFloat(data[1])));
                }

                if (operation.equalsIgnoreCase("withdraw"))
                {
                    socket.send("ok");

                    String[] data = socket.recvStr().split(":");

                    socket.send(Operations.withdraw(accounts.get(data[0]), Float.parseFloat(data[1])));
                }

                if (operation.equalsIgnoreCase("check balance")){

                    String data =socket.recvStr();

                    socket.send(Operations.checkBalance(accounts.get(data)));
                }

            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            if (socket != null)
            {
                socket.close();
            }
        }
    }
}
