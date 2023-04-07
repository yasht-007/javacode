package bank;

import api.AccountHolder;

import java.util.HashMap;

public class AccountDb
{
    private static HashMap<String, AccountHolder> accounts = new HashMap<>();

    public static HashMap<String, AccountHolder> getAccounts()
    {
        return accounts;
    }
}
