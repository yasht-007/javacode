package Model;

public class Menu
{

    private int id;
    private String name;

    public Menu(int id, String name)
    {
        setId(id);

        setName(name);
    }


    public String getName()
    {
        return name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
