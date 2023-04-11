package Model;

public class Food {
    private String id;
    private String name;
    private String description;
    private String price;
    private FoodPreference foodPreference;

    public Food() {

    }

    public Food(String id, String name, String description, String price, FoodPreference foodPreference) {
        setId(id);

        setName(name);

        setDescription(description);

        setPrice(price);

        setFoodPreference(foodPreference);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public FoodPreference getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(FoodPreference foodPreference) {
        this.foodPreference = foodPreference;
    }

}
