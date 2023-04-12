package Repository;

import java.util.HashMap;

public interface MenuRepository
{
    HashMap<Integer, Model.Menu> getMenu();

    void addItemToMenu(int id, String name);
}
