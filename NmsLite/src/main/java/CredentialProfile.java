public class CredentialProfile
{
    private final String discoveryName;
    private final String ip;
    private String userName;
    private String password;
    private int port;

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

    public int getPort()
    {
        return port;
    }

    public CredentialProfile(String discoveryName, String ip, String userName, String password, int port)
    {
        this.discoveryName = discoveryName;
        this.ip = ip;
        this.userName = userName;
        this.password = password;
        this.port = port;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setPort(int port)
    {
        this.port = port;
    }
}
