package api;

public class CredentialProfile
{
    private final String discoveryName;
    private final String ip;
    private String userName;
    private String password;
    private int port;

    public CredentialProfile(User user)
    {
        this.discoveryName = user.getDiscoveryName();

        this.ip = user.getIp();

        this.userName = user.getUserName();

        this.password = user.getPassword();

        this.port = Integer.parseInt(user.getPort());
    }

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

}
