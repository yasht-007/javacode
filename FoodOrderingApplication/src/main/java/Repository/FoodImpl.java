package Repository;

import Model.Food;

import java.util.HashMap;

public class FoodImpl implements FoodRepository {
    private static HashMap<String, HashMap<String, Model.Food>> foodItem = new HashMap<>();

    private FoodImpl() {
    }

    public static synchronized HashMap<String, HashMap<String, Model.Food>> getInstance() {

        if (foodItem == null) {
            foodItem = new HashMap<>();
        }

        return foodItem;
    }


    @Override
    public HashMap<String, Model.Food> getFoodItems(String menuId) {

        foodItem = getInstance();

        return (HashMap<String, Food>) foodItem.get(menuId).clone();
    }

    @Override
    public void addFoodItemToList(String menuId, String foodId, String name, String description, String price, Model.FoodPreference foodPreference) {
        HashMap<String,Model.Food> food = new HashMap<>();

        food.put(foodId, new Model.Food(foodId, name, description, price, foodPreference));

        foodItem.put(menuId,food);
    }
}
