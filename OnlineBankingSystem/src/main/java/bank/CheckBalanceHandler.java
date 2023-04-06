package bank;

import api.AccountHolder;
import org.zeromq.ZMQ;

public class CheckBalanceHandler
{
    private ZMQ.Socket socket;
    private AccountHolder accountdetails;

    public CheckBalanceHandler(ZMQ.Socket socket, AccountHolder accountdetails)
    {
        this.socket = socket;

        this.accountdetails = accountdetails;

    }

    public void handleBalanceCheck()
    {
        socket.send(String.valueOf(accountdetails.getBalance()),0);
    }
}
