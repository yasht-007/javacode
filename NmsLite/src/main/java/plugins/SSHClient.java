package plugins;

import api.User;
import com.jcraft.jsch.*;
import utility.FormatUtility;

import java.io.*;

public class SSHClient
{
    private static final java.util.Properties config = new java.util.Properties();
    private Session session;
    private JSch jSch = null;

    public boolean discover(User user)
    {

        try
        {

            config.put("StrictHostKeyChecking", "no");

            jSch = new JSch();

            session = jSch.getSession(user.getUserName(), user.getIp(), Integer.parseInt(user.getPort()));

            if (session != null)

            {
                session.setPassword(user.getPassword());

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


    public void poll(String userName, String hostName, String password, int port, String dateTime, String discoveryName)
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

                writer.write("Time : " + dateTime + FormatUtility.NEW_LINE_SEPARATOR);

                for (String output; (output = reader.readLine()) != null; )
                {
                    String[] cpuStat = output.split("\\s+");

                    writer.newLine();

                    writer.write("CPU Core: " + cpuStat[3] + FormatUtility.NEW_LINE_SEPARATOR + "CPU Core Interrupt (%): " + cpuStat[9] + FormatUtility.NEW_LINE_SEPARATOR + "CPU Core I/O (%): " + cpuStat[7] + FormatUtility.NEW_LINE_SEPARATOR + "CPU Core System (%): " + cpuStat[6] + FormatUtility.NEW_LINE_SEPARATOR + "CPU Core User (%) " + cpuStat[4] + FormatUtility.NEW_LINE_SEPARATOR + "CPU Core Idle (%): " + cpuStat[13] + FormatUtility.NEW_LINE_SEPARATOR);

                    writer.newLine();

                    writer.flush();
                }

                channel.disconnect();

                channel = session.openChannel("exec");

                command = "vmstat | awk 'NR>2'";

                ((ChannelExec) channel).setCommand(command);

                channel.connect();

                channel.setInputStream(null);

                ((ChannelExec) channel).setErrStream(System.err);

                inputStream = channel.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                writer = new BufferedWriter(new FileWriter(FormatUtility.POLLING_FILE_PATH + discoveryName + "- poll.log/" + "vmstat.txt", true));

                writer.write("Time : " + dateTime + FormatUtility.NEW_LINE_SEPARATOR);

                for (String output; (output = reader.readLine()) != null; )
                {
                    String[] vmStat = output.split("\\s+");

                    writer.newLine();

                    writer.write("system.running.processes=" + vmStat[1]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.blocked.processes=" + vmStat[2]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.swap=" + vmStat[3]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.free=" + vmStat[4]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.buffer=" + vmStat[5]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.cache=" + vmStat[6]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.swapin=" + vmStat[7]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.swapout=" + vmStat[8]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.io.blockin=" + vmStat[9]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.io.blockout=" + vmStat[10]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.interrupt=" + vmStat[11]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.contextswitch=" + vmStat[12]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.cpu.userlevel=" + vmStat[13]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.cpu.systemlevel=" + vmStat[14]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.cpu.idle=" + vmStat[15]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.cpu.wait=" + vmStat[16]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.cpu.steal=" + vmStat[17] + FormatUtility.NEW_LINE_SEPARATOR);

                    writer.flush();

                }

                channel.disconnect();

                channel = session.openChannel("exec");

                command = "free -w | awk 'NR>1'";

                ((ChannelExec) channel).setCommand(command);

                channel.connect();

                channel.setInputStream(null);

                ((ChannelExec) channel).setErrStream(System.err);

                inputStream = channel.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                writer = new BufferedWriter(new FileWriter(FormatUtility.POLLING_FILE_PATH + discoveryName + "- poll.log/" + "memory.txt", true));

                writer.write("Time : " + dateTime + FormatUtility.NEW_LINE_SEPARATOR);

                for (String output; (output = reader.readLine()) != null; )
                {
                    String[] memoryStat = output.split("\\s+");

                    writer.newLine();

                    if (memoryStat[0].equals("Swap:"))
                    {

                        writer.write("Swap:");

                        writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.swap.total=" + memoryStat[1]);

                        writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.swap.used=" + memoryStat[2]);

                        writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.swap.free=" + memoryStat[3]);

                        writer.flush();

                        continue;
                    }

                    writer.write("system.memory.total=" + memoryStat[1]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.used=" + memoryStat[2]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.free=" + memoryStat[3]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.shared=" + memoryStat[4]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.buffer=" + memoryStat[5]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.cache=" + memoryStat[6]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "system.memory.available=" + memoryStat[7]);

                    writer.flush();

                }

                channel.disconnect();

                channel = session.openChannel("exec");

                command = "iostat | awk 'NR>6'";

                ((ChannelExec) channel).setCommand(command);

                channel.connect();

                channel.setInputStream(null);

                ((ChannelExec) channel).setErrStream(System.err);

                inputStream = channel.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                writer = new BufferedWriter(new FileWriter(FormatUtility.POLLING_FILE_PATH + discoveryName + "- poll.log/" + "iostat.txt", true));

                writer.write("Time : " + dateTime + FormatUtility.NEW_LINE_SEPARATOR);

                for (String output; (output = reader.readLine()) != null; )
                {
                    String[] ioStat = output.split("\\s+");

                    writer.newLine();

                    writer.write("Device=" + ioStat[0]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "tps=" + ioStat[1]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "kB_read/s=" + ioStat[2]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "kB_wrtn/s=" + ioStat[3]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "kB_dscd/s=" + ioStat[4]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "kB_read=" + ioStat[5]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "kB_wrtn=" + ioStat[6]);

                    writer.write(FormatUtility.NEW_LINE_SEPARATOR + "kB_dscd=" + ioStat[7] + "\n");

                    writer.newLine();

                    writer.flush();
                }

                channel.disconnect();

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

