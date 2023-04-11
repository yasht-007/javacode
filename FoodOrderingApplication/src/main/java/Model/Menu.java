package Model;

public class Menu {
    private String id;
    private String name;

    public Menu() {

    }

    public Menu(String id, String name) {
        setId(id);

        setName(name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
