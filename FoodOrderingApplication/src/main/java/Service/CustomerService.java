package Service;

import Repository.CustomerImpl;
import Repository.CustomerRepository;

public class CustomerService
{
    CustomerRepository customer = CustomerImpl.getInstance();

    public String loginCustomer(String contactNumber, String password)
    {
        if (customer.isExistingCustomer(contactNumber))
        {
            if (customer.getPasswordByContactNUmber(contactNumber).equals(password))
            {
                return "login success";
            }

            else
            {
                return "incorrect password";
            }
        }

        else
        {
            return "not a customer";
        }
    }

    public String registerCustomer(String name, String contactNumber, String password, String address)
    {
        if (!customer.isExistingCustomer(contactNumber))
        {
            customer.createCustomer(contactNumber, name, address, password);

            return "registration success";
        }

        else
        {
           return "customer already exist";
        }
    }
}
