package Model;

public class Food {
    private String id;
    private String name;
    private String price;

    public Food() {

    }

    public Food(String id, String name, String price) {
        setId(id);

        setName(name);

        setPrice(price);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
