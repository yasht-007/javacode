package Service;

import Repository.MenuImpl;
import org.json.JSONArray;

public class MenuService
{
    Repository.MenuImpl menu = MenuImpl.getInstance();

    public String fetchMenu()
    {
        JSONArray jsonArray = new JSONArray();

        jsonArray.put(menu.getMenu().values());

        return jsonArray.toString();
    }

    public String addToMenu(int id, String name)
    {
        if (!menu.checkNull(id))
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
