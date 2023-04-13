package Service;

import Model.Food;
import Repository.CartImpl;
import Repository.CartRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartService
{
    private final CartRepository cart = CartImpl.getInstance();

    public String addItemToCart(String contactNumber, int menuId, int foodId)
    {
        Model.Food foodItem = new FoodService().getFoodItem(menuId, foodId);

        if (foodItem == null)
        {
            return "food item fetch failed";
        }

        if (cart.getCart(contactNumber) == null)
        {
            cart.addToCart(contactNumber, foodItem);
        }

        else
        {
            cart.appendToCart(contactNumber, foodItem);
        }

        return "success";
    }

    public String getCartItems(String contactNumber)
    {
        ArrayList<Food> cartItems = cart.getCart(contactNumber);

        if (cartItems == null)
        {
            return "no food items in cart";
        }

        JSONArray jsonArray = new JSONArray();

        for (Model.Food food : cartItems)
        {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", food.getId());

            jsonObject.put("name", food.getName());

            jsonObject.put("price", food.getPrice());

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    public String clearCart(String contactNumber)
    {
        cart.clearCart(contactNumber);

        return "success";
    }

}
