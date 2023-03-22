import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Poller
{
    Process process = null;
    BufferedWriter outputWriter = null;
    BufferedReader outputReader = null;
    void startPolling(List<CredentialProfile> credentialProfile, int index)
    {
        String ip = credentialProfile.get(index).getIp();

        String disoveryName = credentialProfile.get(index).getDiscoveryName();

        String dateTime;

        try
        {

            dateTime = new BuildProcess().poll(ip, disoveryName);

            if (dateTime != null)
            {
                new SSHClient().poll(credentialProfile.get(index).getUserName(), ip, credentialProfile.get(index).getPassword(), credentialProfile.get(index).getPort(), dateTime, disoveryName);
            }

            else
            {
                System.out.println("Date Time");
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            try
            {

                if (outputReader != null)
                {
                    outputReader.close();
                }

                if (outputWriter != null)
                {
                    outputWriter.close();
                }


                if (process != null)
                {
                    process.destroy();
                }
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }

    }

}