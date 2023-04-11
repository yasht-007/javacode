package Service;

import Model.Menu;
import Repository.MenuImpl;

import java.util.HashMap;

public class MenuService
{
    Repository.MenuImpl menu = MenuImpl.getInstance();

    public HashMap<String, Menu> displayMenu()
    {
        return menu.getMenu();
    }

    public String addToMenu(String id, String name)
    {
        if (menu.checkNull(id))
        {
            menu.addItemToMenu(id, name);

            return "success";
        }

        else
        {
            return "item already exist";
        }
    }
}
