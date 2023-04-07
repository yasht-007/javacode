package bank;

import api.AccountHolder;
import java.io.DataOutputStream;

public class CheckBalanceHandler
{
    private final DataOutputStream writer;
    private final AccountHolder accountdetails;

    public CheckBalanceHandler(AccountHolder accountdetails)
    {
        this.accountdetails = accountdetails;

        writer = ClientHandler.getWriter();

    }
    public void handleBalanceCheck()
    {
        try
        {

            writer.writeUTF(String.valueOf(accountdetails.getBalance()));

            writer.flush();

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
