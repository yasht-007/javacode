package bank;

import api.AccountHolder;
import org.zeromq.ZMQ;

import java.util.HashMap;

public class DepositHandler
{
    private ZMQ.Socket socket;
    private static AccountHolder accountdetails;
    private static float amount;

    public DepositHandler(ZMQ.Socket socket, AccountHolder accountdetails, float amount)
    {
        this.socket = socket;

        DepositHandler.accountdetails = accountdetails;

        DepositHandler.amount = amount;

    }

    public void handleDeposit()
    {
        accountdetails.setBalance(accountdetails.getBalance() + amount);

        socket.send("deposit success",0);

    }
}
