package Customer;

import Utility.Connection;
import Utility.Const;
import Utility.UserInput;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;


public class Bootstrap
{
    private static final AtomicBoolean loggedIn = new AtomicBoolean(false);
    private static String customerContactNumber = null;

    public static void main(String[] args)
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            while (!Thread.currentThread().isInterrupted())
            {
                System.out.println(Const.NEW_LINE_SEPARATOR + Const.NEW_LINE_SEPARATOR + "Welcome to Online Food Ordering System : " + Const.NEW_LINE_SEPARATOR);

                if (!loggedIn.get() && customerContactNumber == null)
                {
                    displayDashboard();

                    var option = reader.readLine();

                    switch (option)
                    {
                        case "1" -> login(reader);

                        case "2" -> register(reader);

                        case "3" -> Thread.currentThread().interrupt();

                        default ->
                                System.out.println(Const.RED_COLOUR + "Please enter valid choice" + Const.RESET_COLOUR);

                    }

                }

                else
                {
                    try (Connection connection = new Connection(Const.HOST, Const.PORT_NUMBER))
                    {
                        displayMainMenu();

                        var choice = reader.readLine();

                        switch (choice)
                        {
                            case "1" ->
                            {
                                var menuOption = handleMenu(customerContactNumber, connection, reader);

                                if (menuOption != null)
                                {
                                    System.out.println(menuOption);
                                }
                            }

                            case "2" ->
                            {

                            }

                            case "3" ->
                            {
                                loggedIn.set(false);

                                customerContactNumber = null;
                            }
                        }
                    }
                }
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void register(BufferedReader reader) throws Exception
    {
        try (Connection connection = new Connection(Const.HOST, Const.PORT_NUMBER))
        {
            connection.write("register");

            JSONObject request = new JSONObject();

            request.put("name", UserInput.validateInput(Const.NAME_REGEX, Const.NAME_INPUT_MESSAGE, Const.NAME_ERROR_MESSAGE, reader));

            request.put("contactNumber", UserInput.validateInput(Const.CONTACT_REGEX, Const.CONTACT_INPUT_MESSAGE, Const.CONTACT_ERROR_MESSAGE, reader));

            request.put("password", UserInput.validateInput(Const.PASS_REGEX, Const.PASSWORD_INPUT_MESSAGE, Const.PASS_ERROR_MESSAGE, reader));

            request.put("address", UserInput.validateInput(Const.ADDRESS_REGEX, Const.ADDRESS_INPUT_MESSAGE, Const.ADDRESS_ERROR_MESSAGE, reader));

            connection.write(request.toString());

            String response = connection.read();

            if (response == null)
            {
                System.out.println(Const.RED_COLOUR + "register response not received from server" + Const.RESET_COLOUR);

                return;
            }

            JSONObject registerResponse = new JSONObject(response);

            switch (registerResponse.getString("registerResponse"))
            {
                case "registration success" ->
                        System.out.println(Const.GREEN_COLOUR + "Congratulations! You are successfully registered with our system. Please login to continue..." + Const.RESET_COLOUR);

                case "customer already exist" ->
                        System.out.println("You are already our customer. Please login to continue...");

                default -> System.out.println("Server response error for register");

            }

            System.out.println(Const.NEW_LINE_SEPARATOR + "Press enter to continue : ");

            reader.readLine();
        }
    }

    public static void login(BufferedReader reader) throws Exception
    {
        try (Connection connection = new Connection(Const.HOST, Const.PORT_NUMBER))
        {
            String contactNumber = UserInput.validateInput(Const.CONTACT_REGEX, Const.CONTACT_INPUT_MESSAGE, Const.CONTACT_ERROR_MESSAGE, reader);

            String password = UserInput.validateInput(Const.PASS_REGEX, Const.PASSWORD_INPUT_MESSAGE, Const.PASS_ERROR_MESSAGE, reader);

            connection.write("login");

            JSONObject request = new JSONObject();

            request.put("contactNumber", contactNumber);

            request.put("password", password);

            connection.write(request.toString());

            String response = connection.read();

            if (response == null)
            {
                System.out.println(Const.RED_COLOUR + "Login response not received from server" + Const.RESET_COLOUR);

                return;
            }

            JSONObject loginResponse = new JSONObject(response);

            switch (loginResponse.getString("loginResponse"))
            {
                case "login success" ->
                {
                    System.out.println(Const.GREEN_COLOUR + "Login Success!" + Const.RESET_COLOUR);

                    loggedIn.set(true);

                    customerContactNumber = contactNumber;

                }

                case "incorrect password" ->
                        System.out.println(Const.RED_COLOUR + "Entered password is incorrect! Try again by entering correct password" + Const.RESET_COLOUR);

                case "not a customer" ->
                        System.out.println(Const.RED_COLOUR + "Looks like you are not our customer! Please do registration to continue..." + Const.RESET_COLOUR);

                default -> System.out.println("Server response error for login");
            }

            System.out.println(Const.NEW_LINE_SEPARATOR + "Press enter to continue : ");

            reader.readLine();
        }
    }

    public static void displayDashboard()
    {
        System.out.println("1. Login");

        System.out.println("2. Register");

        System.out.println("3. Exit");

        System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your choice here : ");

    }

    public static void displayMainMenu()
    {
        System.out.println("Please select what you want to do....");

        System.out.println("1. See menu");

        System.out.println("2. View cart");

        System.out.println("3. Log Out");

        System.out.print(Const.NEW_LINE_SEPARATOR + "Enter your choice here : ");

    }

    public static String handleMenu(String contactNumber, Connection connection, BufferedReader reader) throws Exception
    {
        System.out.println(Const.NEW_LINE_SEPARATOR + "Select what you want to choose from menu..." + Const.NEW_LINE_SEPARATOR);

        connection.write("getMenu");

        String response = connection.read();

        if (response == null)
        {
            System.out.println(Const.RED_COLOUR + "Menu response not received from server" + Const.RESET_COLOUR);

            return null;
        }

        JSONArray menuResponse = new JSONArray(response);

        for (int index = 0; index < menuResponse.getJSONArray(0).length(); index++)
        {
            JSONObject jsonObject = menuResponse.getJSONArray(0).getJSONObject(index);

            System.out.println(jsonObject.getString("id") + Const.DOT_SEPARATOR + " " + jsonObject.getString("name"));
        }

        System.out.print(Const.NEW_LINE_SEPARATOR + "Please enter your choice here : ");

        return reader.readLine();
    }

}
