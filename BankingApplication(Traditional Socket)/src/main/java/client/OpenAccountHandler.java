package client;

import utility.Const;
import utility.UserInput;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class OpenAccountHandler
{
    private final DataInputStream socketinputreader;
    private final DataOutputStream writer;
    private final BufferedReader reader;

    public OpenAccountHandler(DataInputStream socketinputreader, DataOutputStream writer, BufferedReader reader)
    {
        this.socketinputreader = socketinputreader;

        this.writer = writer;

        this.reader = reader;
    }

    public void handleOpenAccount()
    {
        try
        {

            writer.writeUTF("open account");

            String accountDetails = inputAccountDetails(reader);

            writer.writeUTF(accountDetails);

            String[] status = socketinputreader.readUTF().split(":");

            if (status[0].equalsIgnoreCase("success"))
            {
                System.out.println(Const.NEW_LINE_SEPARATOR + Const.GREEN_COLOUR + "Account successfully created. " + Const.RESET_COLOUR);

                System.out.println(Const.NEW_LINE_SEPARATOR + "Account details : " + Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Customer Id : " + status[1] + Const.NEW_LINE_SEPARATOR + "Account Number : " + status[2]);

                System.out.println(Const.NEW_LINE_SEPARATOR + "Please Login to continue... ");

            }
            else
            {
                System.out.println(Const.RED_COLOUR + "Error creating your account" + Const.RESET_COLOUR + Const.NEW_LINE_SEPARATOR);
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public String inputAccountDetails(BufferedReader reader)
    {
        StringBuilder accountDetails = new StringBuilder();

        try
        {
            while (true)
            {
                accountDetails.append(UserInput.getInput(Const.NAME_REGEX, Const.NAME_INPUT_MESSAGE, Const.NAME_ERROR_MESSAGE, reader)).append(Const.NEW_LINE_SEPARATOR);

                accountDetails.append(UserInput.getInput(Const.DOB_REGEX, Const.DOB_INPUT_MESSAGE, Const.DOB_ERROR_MESSAGE, reader)).append(Const.NEW_LINE_SEPARATOR);

                accountDetails.append(UserInput.getInput(Const.CONTACT_REGEX, Const.CONTACT_INPUT_MESSAGE, Const.CONTACT_ERROR_MESSAGE, reader)).append(Const.NEW_LINE_SEPARATOR);

                accountDetails.append(UserInput.getInput(Const.EMAIL_REGEX, Const.EMAIL_INPUT_MESSAGE, Const.EMAIL_ERROR_MESSAGE, reader)).append(Const.NEW_LINE_SEPARATOR);

                accountDetails.append(UserInput.getAccountType(Const.ACCOUNT_TYPE_INPUT_MESSAGE, Const.ACCOUNT_TYPE_ERROR_MESSAGE, reader)).append(Const.NEW_LINE_SEPARATOR);

                accountDetails.append(UserInput.getInput(Const.PASS_REGEX, Const.PASSWORD_INPUT_MESSAGE, Const.PASS_ERROR_MESSAGE, reader)).append(Const.NEW_LINE_SEPARATOR);

                return accountDetails.toString();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return accountDetails.toString();

    }
}
