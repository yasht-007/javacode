package nms;

import api.User;
import plugins.SSHClient;
import utility.BuildProcess;

public class Discovery
{
    public static boolean ping(User user)
    {
        if (BuildProcess.discover(user.getIp()))
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    public static boolean ssh(User user)
    {
        if (new SSHClient().discover(user))
        {
            return true;
        }

        else
        {
            return false;
        }
    }

}
