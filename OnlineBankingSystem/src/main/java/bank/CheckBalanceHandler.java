package bank;

import api.AccountHolder;
import org.zeromq.ZMQ;

public class CheckBalanceHandler
{
    private final ZMQ.Socket socket;
    private final AccountHolder accountdetails;

    public CheckBalanceHandler(ZMQ.Socket socket, AccountHolder accountdetails)
    {
        this.socket = socket;

        this.accountdetails = accountdetails;

    }

    public void handleBalanceCheck()
    {
        socket.send(String.valueOf(accountdetails.getBalance()), 0);
    }
}
