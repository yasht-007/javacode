package api;

import java.io.BufferedReader;

public class AccountHolder
{
    BufferedReader reader = null;
    private String customerId;
    private String password;
    private String dateOfBirth;
    private String accountNo;
    private String name;
    private String contactNo;
    private String email;
    private String accountType;
    private float balance;

    public AccountHolder(String customerId, String accountNo, String name, String dateOfBirth, String contactNo, String email, String accountType, String password)
    {
        this.customerId = customerId;

        this.password = password;

        this.accountNo = accountNo;

        this.name = name;

        this.dateOfBirth = dateOfBirth;

        this.contactNo = contactNo;

        this.email = email;

        this.accountType = accountType;

        balance = 0;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public String getPassword()
    {
        return password;
    }

    public String getAccountNo()
    {
        return accountNo;
    }

    public String getName()
    {
        return name;
    }

    public String getContactNo()
    {
        return contactNo;
    }

    public String getEmail()
    {
        return email;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public float getBalance()
    {
        return balance;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setContactNo(String contactNo)
    {
        this.contactNo = contactNo;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public void setBalance(float balance)
    {
        this.balance = balance;
    }


}
