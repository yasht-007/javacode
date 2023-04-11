package Repository;

import Model.Customer;

public interface CustomerRepository
{
   String getPasswordByContactNUmber(String contactNo);

    void createCustomer(String contactNo, String name, String address, String password);

    void deleteCustomer(String contactNo);

    boolean isExistingCustomer(String contactNo);
}
