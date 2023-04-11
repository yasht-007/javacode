package Repository;

import java.util.HashMap;

public class CustomerImpl implements CustomerRepository {
    private static HashMap<String, Model.Customer> customer = null;

    private CustomerImpl() {
    }

    public static synchronized HashMap<String, Model.Customer> getInstance() {

        if (customer == null) {
            customer = new HashMap<>();
        }

        return customer;
    }

    @Override
    public Model.Customer findByContactNumber(String contactNo) {
        return customer.get(contactNo);
    }

    @Override
    public void createCustomer(String contactNo, String name, String address) {
        customer = CustomerImpl.getInstance();

        customer.put(contactNo, new Model.Customer(contactNo, name, address));

    }

    @Override
    public void deleteCustomer(String contactNo) {
        customer = CustomerImpl.getInstance();

        customer.remove(contactNo);
    }

    @Override
    public boolean isExistingCustomer(String contactNo) {
        customer = CustomerImpl.getInstance();

        return customer.containsKey(contactNo);
    }
}
