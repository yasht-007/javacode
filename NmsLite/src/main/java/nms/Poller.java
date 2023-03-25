package nms;

import api.CredentialProfile;
import plugins.SSHClient;
import utility.BuildProcess;
import utility.Const;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Poller
{
    public void startPolling(CredentialProfile user)
    {
        Process process = null;

        String ip = user.getIp();

        String disoveryName = user.getDiscoveryName();

        String dateTime;

        try (BufferedWriter outputWriter = null; BufferedReader outputReader = null)
        {
            dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Const.DATE_TIME_FORMAT));

            if (new BuildProcess().poll(ip, disoveryName, dateTime))
            {
                new SSHClient().poll(user.getUserName(), ip, user.getPassword(), user.getPort(), dateTime, disoveryName);
            }

            else
            {
                System.err.println("Device is down");
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

}