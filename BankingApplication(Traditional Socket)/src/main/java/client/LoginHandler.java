package client;

import utility.Const;
import utility.UserInput;
import utility.Validator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.SocketException;

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
            String customerId = UserInput.getInput(Const.CUST_ID_REGEX, Const.CUSTOMER_ID_INPUT_MESSAGE, Const.CUSTOMER_ID_ERROR_MESSAGE, reader);

            String password = UserInput.getInput(Const.PASS_REGEX, Const.PASSWORD_INPUT_MESSAGE, Const.PASS_ERROR_MESSAGE, reader);

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
                                System.out.println(Const.NEW_LINE_SEPARATOR + Const.GREEN_COLOUR + "Thanks for visiting our bank" + Const.RESET_COLOUR + Const.NEW_LINE_SEPARATOR);

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
                                        System.out.println(Const.RED_COLOUR + "Please enter valid deposit amount" + Const.RESET_COLOUR);
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
                                        System.out.println(Const.RED_COLOUR + "Please enter valid withdraw amount" + Const.RESET_COLOUR);

                                    }

                                }

                                case "3" ->
                                {
                                    new Operations().checkBalance(socketinputreader, writer, customerId);

                                    System.out.println();
                                }

                                default ->
                                        System.out.println(Const.NEW_LINE_SEPARATOR + Const.RED_COLOUR + "Please enter valid choice" + Const.RESET_COLOUR);

                            }
                        }
                        catch (NumberFormatException exception)
                        {
                            System.out.println(Const.RED_COLOUR + "Please enter valid amount" + Const.RESET_COLOUR);
                        }

                        System.out.println(Const.NEW_LINE_SEPARATOR + "Press enter to continue (enter) : ");

                        reader.readLine();
                    }
                }

                case "invalid user" ->
                        System.out.println(Const.NEW_LINE_SEPARATOR + Const.RED_COLOUR + "Please enter correct password" + Const.RESET_COLOUR);
            }

        }
        catch (Exception exception)
        {
            if (exception instanceof SocketException)
            {
                System.out.println(Const.RED_COLOUR + "Bank server connection closed" + Const.RESET_COLOUR);

                Thread.currentThread().interrupt();
            }
            else
            {
                exception.printStackTrace();
            }
        }
    }

}
