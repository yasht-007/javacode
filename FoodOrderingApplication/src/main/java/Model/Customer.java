package Model;

public class Customer
{
    private String contactNo;
    private String name;
    private String address;
    private String password;

    public Customer()
    {

    }

    public Customer(String contactNo, String name, String address, String password)
    {
        setContactNo(contactNo);

        setName(name);

        setAddress(address);

        setPassword(password);
    }

    public String getContactNo()
    {
        return contactNo;
    }
    public void setContactNo(String contactNo)
    {
        this.contactNo = contactNo;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getName()
    {
        return name;
    }
    public String getAddress()
    {
        return address;
    }
    public String getPassword()
    {
        return password;
    }
    private void setPassword(String password)
    {
        this.password = password;
    }
}
