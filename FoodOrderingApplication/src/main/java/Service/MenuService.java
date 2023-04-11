package Service;

import Model.Menu;
import Repository.MenuImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuService
{
    Repository.MenuImpl menu = MenuImpl.getInstance();

    public String fetchMenu()
    {
        JSONArray jsonArray = new JSONArray();

        jsonArray.put(menu.getMenu().values());

        return jsonArray.toString();
    }

    public String addToMenu(String id, String name)
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
