package Model;

public class Menu
{
    private int id;
    private String name;

    public Menu()
    {

    }

    public Menu(int id, String name)
    {
        setId(id);

        setName(name);
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
