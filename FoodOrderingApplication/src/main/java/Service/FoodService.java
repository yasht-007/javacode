package Service;

import Model.Food;
import Repository.FoodImpl;

import java.util.HashMap;

public class FoodService
{
    FoodImpl food = FoodImpl.getInstance();

    public HashMap<String, Food> fetchFoodItems(String menuId)
    {
        return food.getFoodItems(menuId);
    }

    public String addFoodItem(String menuId, String foodId, String name,String price)
    {
        if (food.checkNull(menuId))
        {
            if (!food.checkNull(menuId, foodId))
            {
                food.addFoodItemToList(menuId, new Food(foodId, name, price));

                return "success";

            }

            else
            {
                return "food item already exist";
            }
        }

        else
        {
            return "menu doesn't exist";
        }
    }
}
