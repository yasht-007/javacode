package Repository;

import java.util.HashMap;

public interface MenuRepository {
    HashMap<String,Model.Menu> getMenu();
    void addItemToMenu(String id, String name);
    boolean isItemExistInMenu(String menuId);
}
