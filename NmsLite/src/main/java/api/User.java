package api;

import utility.Const;

import java.io.BufferedReader;

import java.io.InputStreamReader;

public class User
{
    BufferedReader reader = null;
    String discoveryName;
    String ip;
    String userName;
    String password;

    String port;

    public String getDiscoveryName()
    {
        return discoveryName;
    }

    public String getIp()
    {
        return ip;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public String getPort()
    {
        return port;
    }

    public void getUserInput()
    {
        try
        {
            reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print(Const.NEW_LINE_SEPARATOR + "Please enter unique discovery name : ");

            discoveryName = reader.readLine().trim();

            System.out.print(Const.NEW_LINE_SEPARATOR + "Please enter unique IP Address that you want to ping : ");

            ip = reader.readLine().trim();

            System.out.print(Const.NEW_LINE_SEPARATOR + "Please enter username of your device : ");

            userName = reader.readLine().trim();

            System.out.print(Const.NEW_LINE_SEPARATOR + "Please enter password of your device : ");

            password = reader.readLine();

            System.out.print(Const.NEW_LINE_SEPARATOR + "Please enter port number of in which ssh service is running : ");

            port = reader.readLine();

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
