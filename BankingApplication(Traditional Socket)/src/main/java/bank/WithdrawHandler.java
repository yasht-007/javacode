package bank;

import api.AccountHolder;
import org.zeromq.ZMQ;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class WithdrawHandler
{
    private static AccountHolder accountdetails;
    private static float amount;
    private final DataOutputStream writer;

    public WithdrawHandler(AccountHolder accountdetails, float amount)
    {
        WithdrawHandler.accountdetails = accountdetails;

        WithdrawHandler.amount = amount;

        writer = ClientHandler.getWriter();
    }

    public void handleWithdraw()
    {
        try
        {

            float balanceAfterUpdate = accountdetails.getBalance() - amount;

            if (balanceAfterUpdate < 0)
            {
                writer.writeUTF("balance can't be less than zero");
            }

            else
            {
                accountdetails.setBalance(balanceAfterUpdate);

                writer.writeUTF("success");
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
