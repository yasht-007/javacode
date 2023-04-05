package bank;

import api.AccountHolder;

public class Operations
{
    public static String deposit(AccountHolder accountHolder, float amount)
    {
        accountHolder.setBalance(accountHolder.getBalance() + amount);

        return "success";
    }

    public static String withdraw(AccountHolder accountHolder, float amount)
    {
        float balanceAfterUpdate = accountHolder.getBalance() - amount;

        if (balanceAfterUpdate < 0)
        {
            return "balance can't be less than zero";
        }

        else
        {
            accountHolder.setBalance(balanceAfterUpdate);

            return "success";
        }
    }

    public static String checkBalance(AccountHolder accountHolder)
    {
        return String.valueOf(accountHolder.getBalance());
    }
}
