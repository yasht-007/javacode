package Repository;

import java.util.HashMap;

public class MenuImpl implements MenuRepository
{

    private static MenuImpl menuImpl = null;
    private static final HashMap<Integer, Model.Menu> menu = new HashMap<>();

    private MenuImpl()
    {
    }

    public static MenuImpl getInstance()
    {

        if (menuImpl == null)
        {
            menuImpl = new MenuImpl();
        }

        return menuImpl;
    }

    @Override
    public HashMap<Integer, Model.Menu> getMenu()
    {
        return (HashMap<Integer, Model.Menu>) menu.clone();
    }

    @Override
    public void addItemToMenu(int id, String name)
    {
        menu.put(id, new Model.Menu(id, name));
    }

    @Override
    public boolean isEmpty(int id)
    {
        return menu.containsKey(id);
    }

}
