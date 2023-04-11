package Repository;

import java.util.HashMap;

public class CustomerImpl implements CustomerRepository
{

    private static CustomerImpl customerImpl = null;
    private static final HashMap<String, Model.Customer> customer = new HashMap<>();

    private CustomerImpl()
    {
    }

    public static synchronized CustomerImpl getInstance()
    {

        if (customerImpl == null)
        {
            customerImpl = new CustomerImpl();
        }

        return customerImpl;
    }


    @Override
    public String getPasswordByContactNUmber(String contactNo)
    {
        return customer.get(contactNo).getPassword();
    }

    @Override
    public void createCustomer(String contactNo, String name, String address, String password)
    {
        customer.put(contactNo, new Model.Customer(contactNo, name, address, password));
    }

    @Override
    public void deleteCustomer(String contactNo)
    {
        customer.remove(contactNo);
    }

    @Override
    public boolean isExistingCustomer(String contactNo)
    {
        return customer.containsKey(contactNo);
    }
}
