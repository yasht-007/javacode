package client;

import utility.Const;
import utility.Validator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import static client.Bootstrap.displayServices;

public class LoginHandler
{
    private final DataInputStream socketinputreader;
    private final DataOutputStream writer;
    private final BufferedReader reader;

    public LoginHandler(DataInputStream socketinputreader, DataOutputStream writer, BufferedReader reader)
    {
        this.socketinputreader = socketinputreader;

        this.writer = writer;

        this.reader = reader;
    }

    public void handleLogin()
    {
        try
        {
            String customerId;

            while (true)
            {
                System.out.println(utility.Const.NEW_LINE_SEPARATOR + "Login to Online Banking : " + utility.Const.NEW_LINE_SEPARATOR);

                System.out.print("Enter Customer Id (Must be of 4 digits) : ");

                customerId = reader.readLine().trim();

                if (Validator.isEmpty(customerId) || !Validator.validatePattern(Const.CUST_ID_REGEX, customerId))
                {
                    System.err.println("Please enter valid customer id");

                }
                else
                {
                    break;
                }
            }


            String password;

            while (true)
            {
                System.out.print(Const.NEW_LINE_SEPARATOR + "Enter Password : ");

                password = reader.readLine().trim();

                if (Validator.isEmpty(password) || !Validator.validatePattern(Const.PASS_REGEX, password))
                {
                    System.err.println("Please enter valid password" + Const.NEW_LINE_SEPARATOR);
                }
                else
                {
                    break;
                }
            }

            writer.writeUTF("login");

            writer.flush();

            String loginRequest = customerId + ":" + password;

            writer.writeUTF(loginRequest);

            writer.flush();

            String loginResponse = socketinputreader.readUTF().toLowerCase().trim();

            switch (loginResponse)
            {
                case "account not exist" ->
                {
                    System.out.print(Const.NEW_LINE_SEPARATOR + "You don't have any account in our bank. Do you want to open new bank account? (Yes/No) ");

                    if (reader.readLine().equalsIgnoreCase("yes"))
                    {

                        new OpenAccountHandler(socketinputreader, writer, reader).handleOpenAccount();

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
                        try
                        {

                            displayServices();

                            System.out.print("Enter your choice here : ");

                            var choice = reader.readLine();

                            if (choice.equalsIgnoreCase("4"))
                            {
                                System.out.println(Const.NEW_LINE_SEPARATOR + "Thanks for visiting our bank" + Const.NEW_LINE_SEPARATOR);

                                break;
                            }

                            switch (choice)
                            {

                                case "1" ->
                                {
                                    System.out.println(Const.NEW_LINE_SEPARATOR + "Enter amount to deposit : ");

                                    String amount = reader.readLine().trim();

                                    if (!Validator.isEmpty(amount) && Validator.validateAmount(Float.parseFloat(amount)))
                                    {
                                        new Operations().deposit(socketinputreader, writer, Float.parseFloat(amount), customerId);
                                    }
                                    else
                                    {
                                        System.err.println("Please enter valid deposit amount");
                                    }
                                }

                                case "2" ->
                                {
                                    System.out.print(Const.NEW_LINE_SEPARATOR + "Enter amount to withdraw : ");

                                    String amount = reader.readLine().trim();

                                    if (!Validator.isEmpty(amount) && Validator.validateAmount(Float.parseFloat(amount)))
                                    {
                                        new Operations().withdraw(socketinputreader, writer, Float.parseFloat(amount), customerId);
                                    }
                                    else
                                    {
                                        System.err.println("Please enter valid withdraw amount");

                                    }

                                }

                                case "3" ->
                                {
                                    new Operations().checkBalance(socketinputreader, writer, customerId);

                                    System.out.println();
                                }

                                default -> System.err.println(Const.NEW_LINE_SEPARATOR + "Please enter valid choice");

                            }
                        }
                        catch (NumberFormatException exception)
                        {
                            System.err.println("Please enter valid amount");
                        }

                        System.out.println(Const.NEW_LINE_SEPARATOR + "Press enter to continue (enter) : ");

                        reader.readLine();
                    }
                }

                case "invalid user" -> System.err.println(Const.NEW_LINE_SEPARATOR + "Please enter correct password");
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
