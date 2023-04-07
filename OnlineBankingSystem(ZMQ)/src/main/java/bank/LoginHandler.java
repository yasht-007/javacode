package bank;

import api.AccountHolder;
import org.zeromq.ZMQ;

import java.util.HashMap;

public class LoginHandler
{
    private final ZMQ.Socket socket;

    private static HashMap<String, AccountHolder> accounts;

    public LoginHandler(ZMQ.Socket socket, HashMap<String, AccountHolder> accounts)
    {
        this.socket = socket;

        LoginHandler.accounts = accounts;
    }

    public void handleLogin()
    {
        String[] credentials = socket.recvStr(0).split(":");

        if (!isAccountExist(credentials[0]))
        {
            socket.send("account not exist", 0);
        }

        else
        {

            if (verifyPassword(credentials[0], credentials[1]))
            {
                socket.send("valid user", 0);
            }

            else
            {
                socket.send("invalid user", 0);
            }

        }


    }

    public static boolean isAccountExist(String customerId)
    {
        return accounts.containsKey(customerId);
    }

    public static boolean verifyPassword(String customerId, String password)
    {
        return accounts.get(customerId).getPassword().equalsIgnoreCase(password);
    }

}
