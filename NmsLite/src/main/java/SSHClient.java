import com.jcraft.jsch.*;

import java.io.*;

public class SSHClient
{
    private static final java.util.Properties config = new java.util.Properties();
    private Session session;
    private JSch jSch = null;

    boolean discover(String userName, String hostName, String password, int port)
    {

        try
        {

            config.put("StrictHostKeyChecking", "no");

            jSch = new JSch();

            session = jSch.getSession(userName, hostName, port);

            if (session != null)

            {
                session.setPassword(password);

                session.setConfig(config);

                session.connect();

                return session.isConnected();

            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            if (session != null)
            {
                session.disconnect();
            }
        }

        return false;
    }


    void poll(String userName, String hostName, String password, int port, String dateTime, String discoveryName)
    {
        Channel channel = null;

        InputStream inputStream = null;

        BufferedWriter writer = null;

        BufferedReader reader = null;

        String command;

        try
        {
            jSch = new JSch();

            config.put("StrictHostKeyChecking", "no");

            session = jSch.getSession(userName, hostName, port);

            if (session != null)
            {
                session.setPassword(password);

                session.setConfig(config);

                session.connect();

                channel = session.openChannel("exec");

                command = "mpstat -P ALL | awk 'NR>3'";

                ((ChannelExec) channel).setCommand(command);

                channel.connect();

                channel.setInputStream(null);

                ((ChannelExec) channel).setErrStream(System.err);

                inputStream = channel.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                writer = new BufferedWriter(new FileWriter(FormatUtility.POLLING_FILE_PATH + discoveryName + "- poll.log/" + "cpustats.txt", true));

                String output;

                writer.write("Time : " + dateTime + FormatUtility.NEW_LINE_SEPARATOR);

                while ((output = reader.readLine()) != null)
                {
                    String[] cpuStat = output.split("\\s+");

                    writer.newLine();

                    writer.write("CPU Core: " + cpuStat[3] + FormatUtility.NEW_LINE_SEPARATOR + "CPU Core Interrupt (%): " + cpuStat[9] + FormatUtility.NEW_LINE_SEPARATOR + "CPU Core I/O (%): " + cpuStat[7] + FormatUtility.NEW_LINE_SEPARATOR + "CPU Core System (%): " + cpuStat[6] + FormatUtility.NEW_LINE_SEPARATOR + "CPU Core User (%) " + cpuStat[4] + FormatUtility.NEW_LINE_SEPARATOR + "CPU Core Idle (%): " + cpuStat[13] + FormatUtility.NEW_LINE_SEPARATOR);

                    writer.newLine();

                    writer.flush();

                }

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
                if (channel != null)
                {
                    channel.disconnect();

                }

                if (session != null)
                {
                    session.disconnect();
                }

                if (reader != null)
                {
                    reader.close();
                }

                if (writer != null)
                {
                    writer.close();
                }

                if (inputStream != null)
                {
                    inputStream.close();
                }

            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

}

