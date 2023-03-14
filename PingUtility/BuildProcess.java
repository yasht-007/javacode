package PingUtility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuildProcess
{
    static String ip = null;

    static Map<String, String> credentialProfile = null;

    public BuildProcess(String ip)
    {
        this.ip = ip;
    }

    public BuildProcess(Map<String, String> credentialProfile)
    {
        this.credentialProfile = credentialProfile;
    }

    InputStream build()
    {
        Process process = null;

        ProcessBuilder processBuilder = null;

        try
        {

            List<String> command = new ArrayList<>();

            command.add("fping");

            command.add("-c");

            command.add("3");

            command.add("-q");

            if (ip != null)
            {
                command.add(ip);

                processBuilder = new ProcessBuilder(command);

                processBuilder.redirectErrorStream(true);

                process = processBuilder.start();

                if (process.waitFor() == 0)
                {

                }

                else
                {

                }
            }


            else
            {
                command.addAll(credentialProfile.keySet());

                processBuilder = new ProcessBuilder(command);

                processBuilder.redirectErrorStream(true);

                process = processBuilder.start();

                if (process.waitFor() == 0)
                {

                }

                else
                {

                }

            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            if (process != null)
            {
                process.destroy();
            }
        }

        return process.getInputStream();

    }

}
