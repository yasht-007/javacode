package Repository;

import Model.Food;

import java.util.HashMap;
import java.util.List;

public interface FoodRepository
{
    List<HashMap<Integer, Food>> getFoodItems(int foodID);

    void addFoodItemToList(int menuId, Model.Food item);

    void addFoodItem(int menuId, Model.Food item);
}
