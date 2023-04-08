package client;

import utility.Const;
import utility.Validator;

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
                System.out.println(Const.NEW_LINE_SEPARATOR + "Account successfully created. ");

                System.out.println(Const.NEW_LINE_SEPARATOR + "Account details : " + Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Customer Id : " + status[1] + Const.NEW_LINE_SEPARATOR + "Account Number : " + status[2]);

                System.out.println(Const.NEW_LINE_SEPARATOR + "Please Login to continue... ");

            }
            else
            {
                System.err.println("Error creating your account" + Const.NEW_LINE_SEPARATOR);
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
                String name, dob, contactNo, emailAddress, password, accountType;

                while (true)
                {

                    System.out.print(Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Enter your full name : ");

                    name = reader.readLine().trim();

                    if (!Validator.isEmpty(name) && Validator.validatePattern(Const.NAME_REGEX, name))
                    {
                        accountDetails.append(name).append(Const.NEW_LINE_SEPARATOR);

                        break;
                    }
                    else
                    {
                        System.err.println("Please enter valid name");
                    }
                }

                while (true)
                {

                    System.out.print(Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Enter your date of birth (MM/DD/YYYY) : ");

                    dob = reader.readLine().trim();

                    if (!dob.isEmpty() && Validator.validatePattern(Const.DOB_REGEX, dob))
                    {
                        accountDetails.append(dob).append(Const.NEW_LINE_SEPARATOR);

                        break;
                    }
                    else
                    {
                        System.err.println("Please enter valid date of birth");
                    }

                }

                while (true)
                {

                    System.out.print(Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Enter your contact number : ");

                    contactNo = reader.readLine().trim();

                    if (!contactNo.isEmpty() && Validator.validatePattern(Const.CONTACT_REGEX, contactNo))
                    {
                        accountDetails.append(contactNo).append(Const.NEW_LINE_SEPARATOR);

                        break;
                    }
                    else
                    {
                        System.err.println("Please enter valid contact number");
                    }
                }

                while (true)
                {

                    System.out.print(Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Enter your email address : ");

                    emailAddress = reader.readLine().trim();

                    if (!emailAddress.isEmpty() && Validator.validatePattern(Const.EMAIL_REGEX, emailAddress))
                    {
                        accountDetails.append(emailAddress).append(Const.NEW_LINE_SEPARATOR);

                        break;
                    }
                    else
                    {
                        System.err.println("Please enter valid email address");
                    }
                }

                while (true)
                {

                    System.out.print(Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Enter type of account (savings/current) : ");

                    accountType = reader.readLine().trim();

                    if (!Validator.isEmpty(accountType) && Validator.validateAccountType(accountType))
                    {
                        accountDetails.append(accountType).append(Const.NEW_LINE_SEPARATOR);

                        break;
                    }
                    else
                    {
                        System.err.println("Please enter only savings or current");
                    }

                }

                while (true)
                {

                    System.out.print(Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Enter password for banking (must be greater than 8 characters): ");

                    password = reader.readLine().trim();

                    if (!password.isEmpty() && Validator.validatePattern(Const.PASS_REGEX, password))
                    {
                        accountDetails.append(password).append(Const.NEW_LINE_SEPARATOR);

                        break;
                    }
                    else
                    {
                        System.err.println(Const.NEW_LINE_SEPARATOR + "Please enter valid password");
                    }

                }

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
