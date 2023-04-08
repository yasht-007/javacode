package bank;

import api.AccountHolder;

import java.io.DataOutputStream;

public class CheckBalanceHandler
{
    private final DataOutputStream writer;
    private final String ip;
    private final AccountHolder accountdetails;

    public CheckBalanceHandler(DataOutputStream writer, AccountHolder accountdetails, String ip)
    {
        this.accountdetails = accountdetails;

        this.writer = writer;

        this.ip = ip;

    }

    public void handleBalanceCheck()
    {
        try
        {

            writer.writeUTF(String.valueOf(accountdetails.getBalance()));

            writer.flush();

            System.out.println(ip+" tried to check balance and got succeeded");

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
