package Repository;

import Model.Customer;

public interface CustomerRepository {
    Model.Customer findByContactNumber(String contactNo);
    void createCustomer(String contactNo, String name, String address);
    void deleteCustomer(String contactNo);
    boolean isExistingCustomer(String contactNo);
}
