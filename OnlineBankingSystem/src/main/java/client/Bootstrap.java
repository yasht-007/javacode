package client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import utility.Const;
import utility.Validator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Bootstrap
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ZContext context = new ZContext();

    private static ZMQ.Socket socket = context.createSocket(SocketType.PAIR);

    public static ZMQ.Socket getSocket()
    {
        return socket;
    }

    public static void displayServices()
    {
        try
        {

            System.out.println(Const.NEW_LINE_SEPARATOR + "Welcome to Online Banking Facility" + Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Please enter select which operation you want to perform : " + Const.NEW_LINE_SEPARATOR);

            System.out.println("1.Deposit amount" + Const.NEW_LINE_SEPARATOR);

            System.out.println("2.Withdraw amount" + Const.NEW_LINE_SEPARATOR);

            System.out.println("3.Check balance" + Const.NEW_LINE_SEPARATOR);

            System.out.println("4.Log out" + Const.NEW_LINE_SEPARATOR);

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    public static String inputAccountDetails()
    {
        StringBuilder accountDetails = new StringBuilder();

        try
        {
            while (true)
            {
                String name, dob, contactNo, emailAddress, password;

                System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your full name (FN MN LN): ");

                name = reader.readLine().trim();

                if (!Validator.isEmpty(name) && Validator.validatePattern(Const.NAME_REGEX, name))
                {
                    accountDetails.append(name).append(Const.NEW_LINE_SEPARATOR);
                }

                else
                {
                    System.err.println(Const.NEW_LINE_SEPARATOR + "Please enter valid name");

                    continue;
                }

                System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your date of birth (MM/DD/YYYY) : ");

                dob = reader.readLine().trim();

                if (!dob.isEmpty() && Validator.validatePattern(Const.DOB_REGEX, dob))
                {
                    accountDetails.append(dob).append(Const.NEW_LINE_SEPARATOR);
                }

                else
                {
                    System.err.println(Const.NEW_LINE_SEPARATOR + "Please enter valid date of birth");

                    continue;
                }

                System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your contact number : ");

                contactNo = reader.readLine().trim();

                if (!contactNo.isEmpty() && Validator.validatePattern(Const.CONTACT_REGEX, contactNo))
                {
                    accountDetails.append(contactNo).append(Const.NEW_LINE_SEPARATOR);
                }

                else
                {
                    System.err.println(Const.NEW_LINE_SEPARATOR + "Please enter valid contact number");

                    continue;
                }

                System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your email address : ");

                emailAddress = reader.readLine().trim();

                if (!emailAddress.isEmpty() && Validator.validatePattern(Const.EMAIL_REGEX, emailAddress))
                {
                    accountDetails.append(emailAddress).append(Const.NEW_LINE_SEPARATOR);
                }

                else
                {
                    System.err.println(Const.NEW_LINE_SEPARATOR + "Please enter valid email address");

                    continue;
                }

                System.out.print(Const.NEW_LINE_SEPARATOR + "Enter type of account (savings/current) : ");

                accountDetails.append(reader.readLine().trim()).append(Const.NEW_LINE_SEPARATOR);

                System.out.print(Const.NEW_LINE_SEPARATOR + "Enter password for banking : ");

                password = reader.readLine().trim();

                if (!password.isEmpty() && Validator.validatePattern(Const.PASS_REGEX, password))
                {
                    accountDetails.append(password).append(Const.NEW_LINE_SEPARATOR);
                }

                else
                {
                    System.err.println(Const.NEW_LINE_SEPARATOR + "Please enter valid password");

                    continue;
                }

                break;
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return accountDetails.toString();

    }

    public static void main(String[] args)
    {

        try
        {

            String customerId;

            String password;

            reader = new BufferedReader(new InputStreamReader(System.in));

            if (socket.connect("tcp://localhost:9999"))
            {
                System.out.println("Connection successful");
            }

            else
            {
                System.err.println("Connection unsuccessful");

                System.exit(0);

            }

            while (!Thread.currentThread().isInterrupted())
            {

                System.out.println(utility.Const.NEW_LINE_SEPARATOR + "Login to NetBanking : " + utility.Const.NEW_LINE_SEPARATOR);

                System.out.print("Enter Customer Id : ");

                customerId = reader.readLine().trim();

                if (Validator.isEmpty(customerId) || !Validator.validatePattern(Const.CUST_ID_REGEX, customerId))
                {
                    System.err.println("Please enter valid cutomer id");

                    System.out.println();

                    continue;
                }

                System.out.print(Const.NEW_LINE_SEPARATOR + "Enter Password : ");

                password = reader.readLine().trim();

                if (Validator.isEmpty(password) || !Validator.validatePattern(Const.PASS_REGEX, password))
                {
                    System.err.println("Please enter valid password" + Const.NEW_LINE_SEPARATOR);

                    continue;
                }

                socket.send("login", 0);

                if (socket.recvStr(0).equalsIgnoreCase("ok"))
                {

                    String loginRequest = customerId + ":" + password;

                    socket.send(loginRequest, 0);

                    String loginResponse = socket.recvStr(0).toLowerCase().trim();

                    switch (loginResponse)
                    {
                        case "account not exist" ->
                        {
                            System.out.print(Const.NEW_LINE_SEPARATOR + "You don't have any account in our bank. Do you want to open new bank account? (Yes/No) ");

                            if (reader.readLine().equalsIgnoreCase("yes"))
                            {
                                socket.send("open account", 0);

                                String accountDetails = inputAccountDetails();

                                socket.send(accountDetails, 0);

                                String[] status = socket.recvStr(0).split(":");

                                if (status[0].equalsIgnoreCase("success"))
                                {
                                    System.out.println(Const.NEW_LINE_SEPARATOR + "Account successfully created. ");

                                    System.out.println(Const.NEW_LINE_SEPARATOR + "Account details : " + Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Customer Id : " + status[1] + Const.NEW_LINE_SEPARATOR + "Account Number : " + status[2]);
                                }

                                else
                                {
                                    System.err.println("Error creating your account" + Const.NEW_LINE_SEPARATOR);
                                }

                            }

                            else
                            {
                                System.out.println(Const.NEW_LINE_SEPARATOR + "Thanks for visiting our bank");
                            }
                        }

                        case "valid user" ->
                        {
                            while (true)
                            {
                                displayServices();

                                System.out.print("Enter your choice here : ");

                                var choice = Integer.parseInt(reader.readLine());

                                if (choice == 4)
                                {
                                    System.out.println(Const.NEW_LINE_SEPARATOR + "Thanks for visiting our bank" + Const.NEW_LINE_SEPARATOR);

                                    break;
                                }

                                switch (choice)
                                {
                                    case 1 ->
                                    {
                                        System.out.println(Const.NEW_LINE_SEPARATOR + "Enter amount to deposit : ");

                                        Operations.deposit(Float.parseFloat(reader.readLine()), customerId);
                                    }

                                    case 2 ->
                                    {
                                        System.out.print(Const.NEW_LINE_SEPARATOR + "Enter amount to withdraw : ");

                                        Operations.withdraw(Double.parseDouble(reader.readLine()), customerId);
                                    }

                                    case 3 ->
                                    {
                                        Operations.checkBalance(customerId);

                                        System.out.println();
                                    }

                                    default ->
                                            System.err.println(Const.NEW_LINE_SEPARATOR + "Please enter valid choice");
                                }

                                System.out.println(Const.NEW_LINE_SEPARATOR + "Press enter to continue (enter) : ");

                                reader.readLine();
                            }
                        }

                        case "invalid user" ->
                        {
                            System.err.println(Const.NEW_LINE_SEPARATOR + "Please enter correct password");
                        }
                    }

                }

            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }

                if (socket != null)
                {
                    socket.close();
                }
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
