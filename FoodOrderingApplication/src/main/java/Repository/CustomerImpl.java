package Repository;

import java.util.HashMap;

public class CustomerImpl implements CustomerRepository
{

    private static CustomerImpl customerImpl = null;
    private static final HashMap<String, Model.Customer> customer = new HashMap<>();

    private CustomerImpl()
    {
    }

    private static synchronized CustomerImpl getInstance()
    {

        if (customerImpl == null)
        {
            customerImpl = new CustomerImpl();
        }

        return customerImpl;
    }

    @Override
    public Model.Customer findByContactNumber(String contactNo)
    {
        return customer.get(contactNo);
    }

    @Override
    public void createCustomer(String contactNo, String name, String address)
    {
        customer.put(contactNo, new Model.Customer(contactNo, name, address));

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
