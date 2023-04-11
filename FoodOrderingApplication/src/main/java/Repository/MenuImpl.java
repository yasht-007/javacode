package Repository;

import java.util.HashMap;

public class MenuImpl implements MenuRepository {
    private static HashMap<String, Model.Menu> menu = null;

    private MenuImpl() {
    }

    public static synchronized HashMap<String, Model.Menu> getInstance() {

        if (menu == null) {
            menu = new HashMap<>();
        }

        return menu;
    }

    @Override
    public HashMap<String, Model.Menu> getMenu() {

        menu = getInstance();

        return (HashMap<String, Model.Menu>) menu.clone();
    }

    @Override
    public void addItemToMenu(String id, String name) {
        menu = getInstance();

        menu.put(id,new Model.Menu(id,name));
    }

}
