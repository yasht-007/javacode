package Service;

import Model.Food;
import Repository.FoodImpl;
import Repository.FoodRepository;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FoodService
{
    private final FoodRepository food = FoodImpl.getInstance();
    public String fetchFoodItems(int menuId)
    {
        JSONArray jsonArray = new JSONArray();

        for (Map<Integer, Model.Food> map : food.getFoodItems(menuId))
        {
            jsonArray.put(map.values());
        }

        return jsonArray.toString();

    }

    public String addFoodItem(int menuId, int foodId, String name, String price)
    {
        AtomicBoolean isItemPresent = new AtomicBoolean(false);

        List<HashMap<Integer, Food>> foodList = food.getFoodItems(menuId);

        if (foodList == null)
        {
            food.addFoodItem(menuId, new Food(foodId, name, price));

            return "success";
        }

        for (Map<Integer, Model.Food> map : foodList)
        {
            if (map.containsKey(foodId))
            {
                isItemPresent.set(true);

                break;
            }
        }

        if (isItemPresent.get())
        {
            return "food item already exist";
        }

        else
        {
            food.appendFoodItemToList(menuId, new Food(foodId, name, price));

            return "success";
        }
    }

    public Model.Food getFoodItem(int menuId, int foodId)
    {
        for (Map<Integer, Model.Food> map : food.getFoodItems(menuId))
        {

            for (Map.Entry<Integer, Food> entry : map.entrySet())
            {
                if (entry.getKey() == foodId)
                {
                    return entry.getValue();
                }
            }

        }

        return null;
    }
}
