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
                try
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
                        String menuId;

                        String foodItemId;

                        displayMainMenu();

                        var choice = UserInput.validateInput(Const.DIGITS_REGEX, Const.NEW_LINE_SEPARATOR + "Enter your choice here : ", Const.INVALID_CHOICE_ERROR_MESSAGE, reader);

                        switch (choice)
                        {
                            case "1" -> handleMenu();

                            case "2" ->
                            {
                                int cartTotal = viewCart(customerContactNumber);

                                if (cartTotal != 0)
                                {
                                    var orderChoice = UserInput.validateInput(Const.YES_OR_NO_REGEX, Const.ORDER_INPUT_MESSAGE, Const.ORDER_ERROR_MESSAGE, reader);

                                    if (orderChoice.equalsIgnoreCase("yes"))
                                    {
                                        processOrder(String.valueOf(cartTotal));
                                    }
                                }
                            }

                            case "3" ->
                            {
                                loggedIn.set(false);

                                customerContactNumber = null;

                            }

                            default ->
                                    System.out.println(Const.RED_COLOUR + "Please enter valid choice" + Const.RESET_COLOUR);
                        }

                        if (!choice.equals("3") && !choice.equals("2"))
                        {
                            {
                                menuId = UserInput.validateInput(Const.DIGITS_REGEX, "Please enter your choice here : ", Const.INVALID_CHOICE_ERROR_MESSAGE, reader);

                                if (menuId != null)
                                {
                                    AtomicBoolean backFoodChoice = new AtomicBoolean(false);

                                    while (!backFoodChoice.get())
                                    {
                                        handleFoodItems(menuId);

                                        System.out.println(Const.NEW_LINE_SEPARATOR + Const.GREEN_COLOUR + "To go back to main menu enter 999" + Const.RESET_COLOUR);

                                        foodItemId = UserInput.validateInput(Const.DIGITS_REGEX, "Please enter your choice to add your order to cart : ", Const.INVALID_CHOICE_ERROR_MESSAGE, reader);

                                        if (foodItemId.equals("999"))
                                        {
                                            backFoodChoice.set(true);
                                        }

                                        else
                                        {
                                            addToCart(customerContactNumber, menuId, foodItemId);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                catch (Exception exception)
                {
                    if (exception.getMessage().equalsIgnoreCase("broken pipe"))
                    {
                        System.out.println(Const.RED_COLOUR + "server is down" + Const.RESET_COLOUR);
                    }

                    else if (exception.getMessage().equalsIgnoreCase("connection refused"))
                    {
                        System.out.println(Const.NEW_LINE_SEPARATOR + Const.RED_COLOUR + "Server unreachable" + Const.RESET_COLOUR);
                    }
                    else
                    {
                        exception.printStackTrace();
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

    }

    public static void handleMenu() throws Exception
    {
        try (Connection connection = new Connection(Const.HOST, Const.PORT_NUMBER))
        {
            System.out.println(Const.NEW_LINE_SEPARATOR + "Select what you want to choose from menu..." + Const.NEW_LINE_SEPARATOR);

            connection.write("getMenu");

            String response = connection.read();

            if (response == null)
            {
                System.out.println(Const.RED_COLOUR + "Menu response not received from server" + Const.RESET_COLOUR);

                return;
            }

            JSONArray menuResponse = new JSONArray(response);

            for (int index = 0; index < menuResponse.getJSONArray(0).length(); index++)
            {
                JSONObject jsonObject = menuResponse.getJSONArray(0).getJSONObject(index);

                System.out.println(jsonObject.getInt("id") + Const.DOT_SEPARATOR + " " + jsonObject.getString("name"));
            }

        }
    }

    public static void handleFoodItems(String menuId) throws Exception
    {
        try (Connection connection = new Connection(Const.HOST, Const.PORT_NUMBER))
        {
            connection.write("getFoodMenu");

            connection.write(new JSONObject().put("menuId", menuId).toString());

            String response = connection.read();

            if (response == null)
            {
                System.out.println(Const.RED_COLOUR + "Food Menu response not received from server" + Const.RESET_COLOUR);

                return;
            }

            JSONArray foodMenuResponse = new JSONArray(response);

            System.out.println(Const.NEW_LINE_SEPARATOR + "Item No" + Const.TAB_SEPARATOR + Const.TAB_SEPARATOR + "Name" + Const.TAB_SEPARATOR + Const.TAB_SEPARATOR + "Price " + Const.NEW_LINE_SEPARATOR);


            for (int index = 0; index < foodMenuResponse.length(); index++)
            {

                JSONObject jsonObject = foodMenuResponse.getJSONArray(index).getJSONObject(0);

                System.out.println(jsonObject.getInt("id") + Const.DOT_SEPARATOR + Const.TAB_SEPARATOR + jsonObject.getString("name") + "\t\t" + jsonObject.getString("price"));
            }

        }

    }

    public static void addToCart(String contactNumber, String menuId, String foodId) throws Exception
    {
        try (Connection connection = new Connection(Const.HOST, Const.PORT_NUMBER))
        {
            connection.write("addFoodItemToCart");

            JSONObject request = new JSONObject();

            request.put("contactNumber", contactNumber);

            request.put("menuId", Integer.parseInt(menuId));

            request.put("foodId", Integer.parseInt(foodId));

            connection.write(request.toString());

            String response = connection.read();

            if (response == null)
            {
                System.out.println(Const.RED_COLOUR + "Add Cart response not received from server" + Const.RESET_COLOUR);

                return;
            }


            switch (response)
            {
                case "success" ->
                        System.out.println(Const.GREEN_COLOUR + "Food item successfully added to cart. You can view your cart on main menu!" + Const.RESET_COLOUR);

                case "food item fetch failed" ->
                        System.out.println(Const.RED_COLOUR + "No such food choice exists!" + Const.RESET_COLOUR);

                default ->
                        System.out.println(Const.RED_COLOUR + "Server response error for add to cart" + Const.RESET_COLOUR);
            }

        }
    }

    public static int viewCart(String contactNumber) throws Exception
    {
        int cartTotal = 0;

        try (Connection connection = new Connection(Const.HOST, Const.PORT_NUMBER))
        {
            connection.write("getCart");

            JSONObject request = new JSONObject();

            request.put("contactNumber", contactNumber);

            connection.write(request.toString());

            String response = connection.read();

            if (response == null)
            {
                System.out.println(Const.RED_COLOUR + "Get Cart response not received from server" + Const.RESET_COLOUR);

                return 0;
            }

            if (response.length() == 21)
            {
                System.out.println(Const.NEW_LINE_SEPARATOR + "Your cart is empty. Please select some food from menu...");

                return 0;
            }

            else
            {
                JSONArray jsonResponse = new JSONArray(response);

                System.out.println(Const.NEW_LINE_SEPARATOR + "Your Cart : ");

                System.out.println(Const.NEW_LINE_SEPARATOR + "Item No" + Const.TAB_SEPARATOR + Const.TAB_SEPARATOR + "Name" + Const.TAB_SEPARATOR + Const.TAB_SEPARATOR + "Price " + Const.NEW_LINE_SEPARATOR);

                int itemCount = 0;

                for (int index = 0; index < jsonResponse.length(); index++)
                {
                    JSONObject jsonObject = jsonResponse.getJSONObject(index);

                    cartTotal += Integer.parseInt(jsonObject.getString("price"));

                    System.out.println(++itemCount + Const.TAB_SEPARATOR + jsonObject.getString("name") + Const.TAB_SEPARATOR + Const.TAB_SEPARATOR + jsonObject.getString("price"));
                }

                System.out.println(Const.NEW_LINE_SEPARATOR + "Cart Total : " + cartTotal);

            }
        }

        return cartTotal;
    }

    public static void processOrder(String cartTotal) throws Exception
    {
        try (Connection connection = new Connection(Const.HOST, Const.PORT_NUMBER))
        {
            connection.write("orderFood");

            connection.write(new JSONObject().put("contactNumber", customerContactNumber).toString());

            if (connection.read().equalsIgnoreCase("success"))
            {
                System.out.println(Const.NEW_LINE_SEPARATOR + Const.GREEN_COLOUR + "Your order worth " + cartTotal + " has been successfully processed" + Const.RESET_COLOUR);

            }

            else
            {
                System.out.println(Const.RED_COLOUR + "Process Order response not received from server" + Const.RESET_COLOUR);
            }
        }
    }

}