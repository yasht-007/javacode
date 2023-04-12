package Repository;

import Model.Food;

import java.util.ArrayList;
import java.util.HashMap;


public class FoodImpl implements FoodRepository
{

    private static FoodImpl foodImpl = null;
    private static final HashMap<Integer, ArrayList<HashMap<Integer, Food>>> foodItem = new HashMap<>();

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
    public ArrayList<HashMap<Integer, Food>> getFoodItems(int menuId)
    {
        return foodItem.get(menuId) == null ? null : (ArrayList<HashMap<Integer, Food>>) foodItem.get(menuId).clone();
    }

    @Override
    public void addFoodItemToList(int menuId, Food item)
    {
        HashMap<Integer, Model.Food> food = new HashMap<>();

        food.put(item.getId(), item);

        foodItem.get(menuId).add(food);
    }

    @Override
    public void addFoodItem(int menuId, Food item)
    {
        HashMap<Integer, Model.Food> food = new HashMap<>();

        food.put(item.getId(), item);

        ArrayList<HashMap<Integer, Model.Food>> list = new ArrayList<>();

        list.add(food);

        foodItem.put(menuId, list);
    }

}
