package Repository;

import Model.Food;

import java.util.HashMap;

public class FoodImpl implements FoodRepository
{

    private static FoodImpl foodImpl = null;
    private static final HashMap<String, HashMap<String, Model.Food>> foodItem = new HashMap<>();

    private FoodImpl()
    {
    }

    public static synchronized FoodImpl getInstance()
    {

        if (foodImpl == null)
        {
            foodImpl = new FoodImpl();
        }

        return foodImpl;
    }


    @Override
    public HashMap<String, Model.Food> getFoodItems(String menuId)
    {
        return (HashMap<String, Food>) foodItem.get(menuId).clone();
    }

    public void addFoodItemToList(String menuId, Model.Food item)
    {
        HashMap<String, Model.Food> food = new HashMap<>();

        food.put(item.getId(), item);

        foodItem.put(menuId, food);
    }

    public boolean checkNull(String menuId)
    {
        return foodItem.containsKey(menuId);
    }
    public boolean checkNull(String menuId, String foodId)
    {
        return foodItem.get(menuId).containsKey(foodId);
    }
}
