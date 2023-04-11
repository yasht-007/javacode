package Repository;

import Model.Food;
import Model.Menu;

import java.util.HashMap;

public interface FoodRepository
{
    HashMap<String, Model.Food> getFoodItems(String menuId);

    void addFoodItemToList(String menuId, Model.Food item);
}
