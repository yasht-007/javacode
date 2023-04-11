package Repository;

import Model.Menu;

import java.util.HashMap;

public class MenuImpl implements MenuRepository, NullCheck
{

    private static MenuImpl menuImpl = null;
    private static final HashMap<String, Model.Menu> menu = new HashMap<>();

    private MenuImpl()
    {
    }

    public static synchronized MenuImpl getInstance()
    {

        if (menuImpl == null)
        {
            menuImpl = new MenuImpl();
        }

        return menuImpl;
    }

    @Override
    public HashMap<String, Model.Menu> getMenu()
    {
        return (HashMap<String, Model.Menu>) menu.clone();
    }

    @Override
    public void addItemToMenu(String id, String name)
    {
        menu.put(id, new Model.Menu(id, name));
    }

    @Override
    public boolean isItemExistInMenu(String menuId)
    {
        return menu.containsKey(menuId);
    }

    @Override
    public boolean checkNull(String id)
    {
        return menu.containsKey(id);
    }
}
