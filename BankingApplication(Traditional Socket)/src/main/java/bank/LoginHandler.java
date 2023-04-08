package bank;

import api.AccountHolder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;

public class LoginHandler
{
    private final DataInputStream reader;
    private final DataOutputStream writer;

    private final String ip;
    private static final HashMap<String, AccountHolder> accounts = AccountDb.getAccounts();

    LoginHandler(DataInputStream reader, DataOutputStream writer, String ip)
    {
        this.reader = reader;

        this.writer = writer;

        this.ip = ip;
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

                System.out.println(ip + " tried for login and account not exist");
            }

            else
            {

                if (verifyPassword(credentials[0], credentials[1]))
                {
                    writer.writeUTF("valid user");

                    writer.flush();

                    System.out.println(ip + " tried for login and it's a valid user");
                }

                else
                {
                    writer.writeUTF("invalid user");

                    writer.flush();

                    System.out.println(ip + " tried for login and it's an invalid user");

                }

            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public boolean isAccountExist(String customerId)
    {
        return accounts.containsKey(customerId);
    }

    public boolean verifyPassword(String customerId, String password)
    {
        return accounts.get(customerId).getPassword().equalsIgnoreCase(password);
    }

}
