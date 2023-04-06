package bank;

import api.AccountHolder;
import org.zeromq.ZMQ;
import utility.Const;

import java.util.HashMap;

public class OpenAccountHandler implements Runnable
{
    public static int LAST_ASSIGNED_CUSTOMER_ID = 1000;
    public static int LAST_ASSIGNED_ACCOUNT_NO = 4000000;
    private ZMQ.Socket socket;
    private HashMap<String, AccountHolder> accounts;

    public OpenAccountHandler(ZMQ.Socket socket, HashMap<String, AccountHolder> accounts)
    {
        this.socket = socket;

        this.accounts = accounts;
    }

    @Override
    public void run()
    {
        handleOpenAccount();
    }

    public void handleOpenAccount()
    {
        String[] accountDetails = socket.recvStr(0).split("\n");

        String customerId = String.valueOf(LAST_ASSIGNED_CUSTOMER_ID++);

        String accountNo = String.valueOf(LAST_ASSIGNED_ACCOUNT_NO++);

        AccountHolder accountHolder = new AccountHolder(customerId, accountNo, accountDetails[0], accountDetails[1], accountDetails[2], accountDetails[3], accountDetails[4], accountDetails[5]);

        accounts.put(customerId, accountHolder);

        socket.send("success:" + customerId + ":" + accountNo, 0);

    }
}
