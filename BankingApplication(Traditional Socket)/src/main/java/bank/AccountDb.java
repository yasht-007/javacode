package bank;

import api.AccountHolder;

import java.util.HashMap;

public class AccountDb
{
    private final HashMap<String, AccountHolder> accounts = new HashMap<>();

    public HashMap<String, AccountHolder> getAccounts()
    {
        return accounts;
    }
}
