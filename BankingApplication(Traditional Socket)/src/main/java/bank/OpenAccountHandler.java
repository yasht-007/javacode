package bank;

import api.AccountHolder;
import utility.Const;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.util.HashMap;

public class OpenAccountHandler
{
    public static int LAST_ASSIGNED_CUSTOMER_ID = 1000;
    public static int LAST_ASSIGNED_ACCOUNT_NO = 4000000;
    private final DataInputStream reader;
    private final DataOutputStream writer;

    private final String ip;
    private static final HashMap<String, AccountHolder> accounts = AccountDb.getAccounts();


    public OpenAccountHandler(DataInputStream reader, DataOutputStream writer, String ip)
    {
        this.reader = reader;

        this.writer = writer;

        this.ip = ip;
    }

    public void handleOpenAccount()
    {
        try
        {

            String[] accountDetails = reader.readUTF().split(Const.NEW_LINE_SEPARATOR);

            String customerId = String.valueOf(LAST_ASSIGNED_CUSTOMER_ID++);

            String accountNo = String.valueOf(LAST_ASSIGNED_ACCOUNT_NO++);

            AccountHolder accountHolder = new AccountHolder(customerId, accountNo, accountDetails[0], accountDetails[1], accountDetails[2], accountDetails[3], accountDetails[4], accountDetails[5]);

            accounts.put(customerId, accountHolder);

            System.out.println(ip + " tried to open account");

            writer.writeUTF("success:" + customerId + ":" + accountNo);

            writer.flush();

        }

        catch (Exception exception)
        {
            if (exception instanceof EOFException)
            {
                System.out.println(Const.RED_COLOUR + "client " + ip + " disconnected" + Const.RESET_COLOUR);
            }

            else
            {
                exception.printStackTrace();
            }
        }
    }
}
