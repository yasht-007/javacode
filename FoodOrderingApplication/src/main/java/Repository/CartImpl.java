package Repository;


import Model.Food;

import java.util.ArrayList;
import java.util.HashMap;

public class CartImpl implements CartRepository
{

    private static CartImpl cartImpl = null;
    private static final HashMap<String, ArrayList<Model.Food>> cart = new HashMap<>();

    private CartImpl()
    {
    }

    public static synchronized CartImpl getInstance()
    {

        if (cartImpl == null)
        {
            cartImpl = new CartImpl();
        }

        return cartImpl;
    }


    @Override
    public ArrayList<Model.Food> getCart(String contactNumber)
    {
        return cart.get(contactNumber) == null ? null : (ArrayList<Food>) cart.get(contactNumber).clone();
    }

    @Override
    public void addToCart(String contactNumber, Model.Food foodItem)
    {
        ArrayList<Model.Food> list = new ArrayList<>();

        list.add(foodItem);

        cart.put(contactNumber, list);
    }

    public void clearCart(String contactNumber)
    {
        cart.remove(contactNumber);
    }

    @Override
    public void appendToCart(String contactNumber, Model.Food foodItem)
    {
        cart.get(contactNumber).add(foodItem);
    }

    @Override
    public boolean isEmpty(String contactNumber)
    {
        return cart.containsKey(contactNumber);
    }

}

