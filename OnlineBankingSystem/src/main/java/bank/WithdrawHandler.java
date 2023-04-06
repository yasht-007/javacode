package bank;

import api.AccountHolder;
import org.zeromq.ZMQ;

public class WithdrawHandler
{
    private ZMQ.Socket socket;
    private static AccountHolder accountdetails;
    private static float amount;

    public WithdrawHandler(ZMQ.Socket socket, AccountHolder accountdetails, float amount)
    {
        this.socket = socket;

        WithdrawHandler.accountdetails = accountdetails;

        WithdrawHandler.amount = amount;
    }

    public void handleWithdraw()
    {
        float balanceAfterUpdate = accountdetails.getBalance() - amount;

        if (balanceAfterUpdate < 0)
        {
            socket.send("balance can't be less than zero", 0);
        }

        else
        {
            accountdetails.setBalance(balanceAfterUpdate);

            socket.send("success", 0);
        }

    }
}
