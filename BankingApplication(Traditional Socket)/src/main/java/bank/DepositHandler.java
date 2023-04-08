package bank;

import api.AccountHolder;
import org.zeromq.ZMQ;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class DepositHandler
{
    private final DataOutputStream writer;
    private static AccountHolder accountdetails;
    private final float amount;
    private final String ip;

    public DepositHandler(DataOutputStream writer, AccountHolder accountdetails, float amount, String ip)
    {

        DepositHandler.accountdetails = accountdetails;

        this.amount = amount;

        this.writer = writer;

        this.ip = ip;

    }

    public void handleDeposit()
    {
        try
        {

            accountdetails.setBalance(accountdetails.getBalance() + amount);

            writer.writeUTF("deposit success");

            writer.flush();

            System.out.println(ip+" tried for depositing "+amount);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
