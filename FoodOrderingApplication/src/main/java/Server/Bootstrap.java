package Server;

import Service.CustomerService;
import Service.FoodService;
import Service.MenuService;
import Utility.Const;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bootstrap
{
    private static final ExecutorService executor = Executors.newFixedThreadPool(Const.NO_OF_THREADS);

    public static void main(String[] args)
    {

        try (ServerSocket serverSocket = new ServerSocket(9999);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));)
        {
            System.out.println("Server started at port 9999");

            MenuService menuService = new MenuService();

            addMenuItems(menuService);

            FoodService foodService = new FoodService();

            addFoodItems(foodService);

            while (!Thread.currentThread().isInterrupted())
            {
                Socket socket = serverSocket.accept();

                System.out.println(Const.GREEN_COLOUR + socket.getRemoteSocketAddress() + " is connected" + Const.RESET_COLOUR);

                DataOutputStream connectionOutput = new DataOutputStream(socket.getOutputStream());

                DataInputStream connectionInput = new DataInputStream(socket.getInputStream());

                executor.execute(() ->
                {
                    try
                    {
                        String task = connectionInput.readUTF();

                        switch (task)
                        {
                            case "login" ->
                            {
                                JSONObject data = new JSONObject(connectionInput.readUTF());

                                connectionOutput.writeUTF(new JSONObject().put("loginResponse", new CustomerService().loginCustomer(data.getString("contactNumber"), data.getString("password"))).toString());

                                connectionOutput.flush();
                            }

                            case "register" ->
                            {
                                JSONObject data = new JSONObject(connectionInput.readUTF());

                                connectionOutput.writeUTF(new JSONObject().put("registerResponse", new CustomerService().registerCustomer(data.getString("name"), data.getString("contactNumber"), data.getString("password"), data.getString("password"))).toString());

                                connectionOutput.flush();

                            }

                            case "getMenu" ->
                            {
                                connectionOutput.writeUTF(new MenuService().fetchMenu());

                                connectionOutput.flush();
                            }

                            case "getFoodMenu" ->
                            {
                                JSONObject data = new JSONObject(connectionInput.readUTF());

                                connectionOutput.writeUTF(new FoodService().fetchFoodItems(Integer.parseInt(data.getString("menuId"))));
                            }
                        }
                    }
                    catch (Exception exception)
                    {
                        if (exception instanceof EOFException || exception instanceof SocketException)
                        {

                        }
                        else
                        {
                            exception.printStackTrace();
                        }
                    }
                });
            }

        }
        catch (Exception exception)
        {
            if (exception instanceof EOFException || exception instanceof SocketException)
            {

            }

            else
            {
                exception.printStackTrace();
            }
        }

        finally
        {
            executor.shutdown();
        }
    }

    public static void addMenuItems(MenuService menuService)
    {
        menuService.addToMenu(1, "Starters");

        menuService.addToMenu(2, "Dishes");

        menuService.addToMenu(3, "Thali");

        menuService.addToMenu(4, "Drinks");

        menuService.addToMenu(5, "Ice creams");
    }

    public static void addFoodItems(FoodService foodService)
    {
        foodService.addFoodItem(1, 1, "Samosa Chaat", "50");

        foodService.addFoodItem(1, 2, "Aloo Tikki", "30");

        foodService.addFoodItem(1, 3, "Corn Chaat", "60");

        foodService.addFoodItem(2, 1, "Paneer Tikka", "150");

        foodService.addFoodItem(2, 2, "Mix Veg", "150");

        foodService.addFoodItem(2, 3, "Chole Bhatoore", "90");

        foodService.addFoodItem(2, 4, "Malai Kopta", "200");

        foodService.addFoodItem(3, 1, "Fix Punjabi Thali", "200");

        foodService.addFoodItem(3, 2, "Fix Gujarati Thali", "180");

        foodService.addFoodItem(4, 1, "Coco Coala", "20");

        foodService.addFoodItem(4, 2, "Fainta", "30");

        foodService.addFoodItem(4, 3, "Pepsi", "20");

        foodService.addFoodItem(4, 4, "Thumps Up", "20");

        foodService.addFoodItem(5, 1, "Chocolate scoop", "30");

        foodService.addFoodItem(5, 2, "Vanilla scoop", "30");

        foodService.addFoodItem(5, 3, "Amerian Dryfruit", "50");

        foodService.addFoodItem(5, 4, "Black Currant", "50");
    }

}
