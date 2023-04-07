package bank;

import api.AccountHolder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;

public class LoginHandler
{
    private final DataInputStream reader;
    private final DataOutputStream writer;
    private static HashMap<String, AccountHolder> accounts = AccountDb.getAccounts();

    LoginHandler()
    {
        reader = ClientHandler.getReader();

        writer = ClientHandler.getWriter();
    }

    public void handleLogin()
    {
        try
        {

            String[] credentials = reader.readUTF().split(":");

            if (!isAccountExist(credentials[0]))
            {
                writer.writeUTF("account not exist");

                writer.flush();
            }

            else
            {

                if (verifyPassword(credentials[0], credentials[1]))
                {
                    writer.writeUTF("valid user");

                    writer.flush();
                }

                else
                {
                    writer.writeUTF("invalid user");

                    writer.flush();
                }

            }


        }

        catch (Exception exception)
        {
            exception.printStackTrace();
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
