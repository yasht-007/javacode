package bank;

import api.AccountHolder;
import org.zeromq.ZMQ;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class DepositHandler
{
    private final DataOutputStream writer;
    private static AccountHolder accountdetails;
    private static float amount;

    public DepositHandler(AccountHolder accountdetails, float amount)
    {

        DepositHandler.accountdetails = accountdetails;

        DepositHandler.amount = amount;

        writer = ClientHandler.getWriter();

    }

    public void handleDeposit()
    {
        try
        {

            accountdetails.setBalance(accountdetails.getBalance() + amount);

            writer.writeUTF("deposit success");

            writer.flush();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
