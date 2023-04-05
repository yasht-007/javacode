package client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import utility.Const;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Bootstrap
{
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

    public static void main(String[] args)
    {
        BufferedReader reader = null;

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

            while (true)
            {

                System.out.println(utility.Const.NEW_LINE_SEPARATOR + "Login to NetBanking : " + utility.Const.NEW_LINE_SEPARATOR);

                System.out.print("Enter Customer Id : ");

                customerId = reader.readLine().trim();

                System.out.print("Enter Password : ");

                password = reader.readLine().trim();

                socket.send("login");

                if (socket.recvStr().equalsIgnoreCase("ok"))
                {
                    String loginRequest = customerId + ":" + password;

                    socket.send(loginRequest);

                    String loginResponse = socket.recvStr();

                    if (loginResponse.equalsIgnoreCase("invalid password"))
                    {
                        System.err.println("Please enter correct password");
                    }

                    else if (loginResponse.equalsIgnoreCase("valid account holder"))
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
                                    System.out.println("Enter amount to deposit : ");

                                    Operations.deposit(Double.parseDouble(reader.readLine()), customerId);
                                }

                                case 2 ->
                                {
                                    System.out.println("Enter amount to withdraw : ");

                                    Operations.withdraw(Double.parseDouble(reader.readLine()), customerId);
                                }

                                case 3 ->
                                {
                                    Operations.checkBalance(customerId);
                                }

                                default -> System.err.println("Please enter valid choice");
                            }

                            System.out.println("Press enter to continue (enter) : ");

                            reader.readLine();
                        }
                    }

                    else
                    {
                        System.out.print(Const.NEW_LINE_SEPARATOR + "You don't have any account in our bank. Do you want to open new bank account? (Yes/No) ");

                        if (reader.readLine().equalsIgnoreCase("yes"))
                        {
                            socket.send("open account");

                            StringBuilder accountDetails = new StringBuilder();

                            reader = new BufferedReader(new InputStreamReader(System.in));

                            System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your full name : ");

                            accountDetails.append(reader.readLine().trim()).append(Const.NEW_LINE_SEPARATOR);

                            System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your date of birth : ");

                            accountDetails.append(reader.readLine().trim()).append(Const.NEW_LINE_SEPARATOR);

                            System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your contact number : ");

                            accountDetails.append(reader.readLine().trim()).append(Const.NEW_LINE_SEPARATOR);

                            System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your email address : ");

                            accountDetails.append(reader.readLine().trim()).append(Const.NEW_LINE_SEPARATOR);

                            System.out.print(Const.NEW_LINE_SEPARATOR + "Enter type of account (savings/current) : ");

                            accountDetails.append(reader.readLine().trim()).append(Const.NEW_LINE_SEPARATOR);

                            System.out.print(Const.NEW_LINE_SEPARATOR + "Enter password for banking : ");

                            accountDetails.append(reader.readLine().trim()).append(Const.NEW_LINE_SEPARATOR);

                            socket.send(accountDetails.toString());

                            String[] status = socket.recvStr().split(" ");

                            if (status[0].equalsIgnoreCase("success"))
                            {
                                System.out.println(Const.NEW_LINE_SEPARATOR + "Account successfully created. ");

                                System.out.println(Const.NEW_LINE_SEPARATOR + "Account details : " + Const.NEW_LINE_SEPARATOR + "Customer Id : " + status[1] + Const.NEW_LINE_SEPARATOR + "Account Number : " + status[2]);

                                System.out.println(Const.NEW_LINE_SEPARATOR + "Press enter to login into our banking facility (enter) : ");

                                reader.readLine();

                            }

                            else
                            {
                                System.err.println("Error creating your account" + Const.NEW_LINE_SEPARATOR);
                            }

                        }

                        else
                        {
                            System.out.println("Thanks for visiting our bank" + Const.NEW_LINE_SEPARATOR);
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
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
