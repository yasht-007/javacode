package Repository;

import java.util.ArrayList;

public interface CartRepository
{
    ArrayList<Model.Food> getCart(String contactNumber);
    void addToCart(String contactNumber, Model.Food foodItem);
    boolean isEmpty(String contactNumber);
    void appendToCart(String contactNumber, Model.Food foodItem);
}
