package Repository;

public interface CustomerRepository
{
   String getPasswordByContactNUmber(String contactNo);
    void createCustomer(String contactNo, String name, String address, String password);
    boolean isExistingCustomer(String contactNo);
}
