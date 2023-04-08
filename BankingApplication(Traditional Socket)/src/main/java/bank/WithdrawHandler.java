package bank;

import api.AccountHolder;
import org.zeromq.ZMQ;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class WithdrawHandler
{
    private static AccountHolder accountdetails;
    private final float amount;
    private final String ip;
    private final DataOutputStream writer;

    public WithdrawHandler(DataOutputStream writer, AccountHolder accountdetails, float amount,String ip)
    {
        WithdrawHandler.accountdetails = accountdetails;

        this.amount = amount;

        this.ip = ip;

        this.writer = writer;
    }

    public void handleWithdraw()
    {
        try
        {

            float balanceAfterUpdate = accountdetails.getBalance() - amount;

            if (balanceAfterUpdate < 0)
            {
                writer.writeUTF("balance can't be less than zero");

                writer.flush();

                System.out.println(ip+" tried for withdrawing "+amount+" and failed because amount entered is zero or less than zero");

            }

            else
            {
                accountdetails.setBalance(balanceAfterUpdate);

                writer.writeUTF("success");

                writer.flush();

                System.out.println(ip+" tried for withdrawing "+amount+" and succeeded");

            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
