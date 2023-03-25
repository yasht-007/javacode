package plugins;

import api.User;
import com.jcraft.jsch.*;
import utility.Const;

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

                command = "mpstat -P ALL | awk 'NR>3';echo '-vmstat';vmstat | awk 'NR>2';echo '-memory';free -w | awk 'NR>1';echo '-disk';iostat | awk 'NR>6';";

                ((ChannelExec) channel).setCommand(command);

                channel.connect();

                channel.setInputStream(null);

                ((ChannelExec) channel).setErrStream(System.err);

                inputStream = channel.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                int flag = 0;

                writer = new BufferedWriter(new FileWriter(Const.POLLING_FILE_PATH + discoveryName + "- poll.log/" + "cpustats.txt", true));

                writer.write("Time : " + dateTime + Const.NEW_LINE_SEPARATOR);


                for (String output; (output = reader.readLine()) != null; )
                {
                    String delimeter = output.split("\\s+")[0];

                    String[] stat = output.split("\\s+");

                    switch (delimeter)
                    {
                        case "-vmstat" ->
                        {
                            flag = 1;

                            writer = new BufferedWriter(new FileWriter(Const.POLLING_FILE_PATH + discoveryName + "- poll.log/" + "vmstat.txt", true));

                            writer.write("Time : " + dateTime + Const.NEW_LINE_SEPARATOR);

                            continue;
                        }

                        case "-memory" ->
                        {
                            flag = 2;

                            writer = new BufferedWriter(new FileWriter(Const.POLLING_FILE_PATH + discoveryName + "- poll.log/" + "memory.txt", true));

                            writer.write("Time : " + dateTime + Const.NEW_LINE_SEPARATOR);

                            continue;
                        }

                        case "-disk" ->
                        {
                            flag = 3;

                            writer = new BufferedWriter(new FileWriter(Const.POLLING_FILE_PATH + discoveryName + "- poll.log/" + "iostat.txt", true));

                            writer.write("Time : " + dateTime + Const.NEW_LINE_SEPARATOR);

                            continue;
                        }

                    }

                    switch (flag)
                    {
                        case 0 ->
                        {
                            writer.newLine();

                            writer.write("CPU Core: " + stat[3] + Const.NEW_LINE_SEPARATOR + "CPU Core Interrupt (%): " + stat[9] + Const.NEW_LINE_SEPARATOR + "CPU Core I/O (%): " + stat[7] + Const.NEW_LINE_SEPARATOR + "CPU Core System (%): " + stat[6] + Const.NEW_LINE_SEPARATOR + "CPU Core User (%) " + stat[4] + Const.NEW_LINE_SEPARATOR + "CPU Core Idle (%): " + stat[13] + Const.NEW_LINE_SEPARATOR);

                            writer.newLine();

                            writer.flush();
                        }

                        case 1 ->
                        {
                            writer.newLine();

                            writer.write("system.running.processes=" + stat[1] + Const.NEW_LINE_SEPARATOR + "system.blocked.processes=" + stat[2] + Const.NEW_LINE_SEPARATOR + "system.memory.swap=" + stat[3] + Const.NEW_LINE_SEPARATOR + "system.memory.free=" + stat[4] + Const.NEW_LINE_SEPARATOR + "system.memory.buffer=" + stat[5] + Const.NEW_LINE_SEPARATOR + "system.memory.cache=" + stat[6] + Const.NEW_LINE_SEPARATOR + "system.memory.swapin=" + stat[7] + Const.NEW_LINE_SEPARATOR + "system.memory.swapout=" + stat[8] + Const.NEW_LINE_SEPARATOR + "system.io.blockin=" + stat[9] + Const.NEW_LINE_SEPARATOR + "system.io.blockout=" + stat[10] + Const.NEW_LINE_SEPARATOR + "system.interrupt=" + stat[11] + Const.NEW_LINE_SEPARATOR + "system.contextswitch=" + stat[12] + Const.NEW_LINE_SEPARATOR + "system.cpu.userlevel=" + stat[13] + Const.NEW_LINE_SEPARATOR + "system.cpu.systemlevel=" + stat[14] + Const.NEW_LINE_SEPARATOR + "system.cpu.idle=" + stat[15] + Const.NEW_LINE_SEPARATOR + "system.cpu.wait=" + stat[16] + Const.NEW_LINE_SEPARATOR + "system.cpu.steal=" + stat[17] + Const.NEW_LINE_SEPARATOR);

                            writer.newLine();

                            writer.flush();
                        }

                        case 2 ->
                        {
                            writer.newLine();

                            if (stat[0].equals("Swap:"))
                            {
                                writer.write("Swap: " + Const.NEW_LINE_SEPARATOR + "system.swap.total=" + stat[1] + Const.NEW_LINE_SEPARATOR + "system.swap.used=" + stat[2] + Const.NEW_LINE_SEPARATOR + "system.swap.free=" + stat[3] + Const.NEW_LINE_SEPARATOR);

                                writer.newLine();

                                writer.flush();

                                continue;
                            }

                            writer.write("Memory: " + Const.NEW_LINE_SEPARATOR + "system.memory.total=" + stat[1] + Const.NEW_LINE_SEPARATOR + "system.memory.used=" + stat[2] + Const.NEW_LINE_SEPARATOR + "system.memory.free=" + stat[3] + Const.NEW_LINE_SEPARATOR + "system.memory.shared=" + stat[4] + Const.NEW_LINE_SEPARATOR + "system.memory.buffer=" + stat[5] + Const.NEW_LINE_SEPARATOR + "system.memory.cache=" + stat[6] + Const.NEW_LINE_SEPARATOR + "system.memory.available=" + stat[7] + Const.NEW_LINE_SEPARATOR);

                            writer.newLine();

                            writer.flush();
                        }

                        case 3 ->
                        {
                            writer.newLine();

                            writer.write("Device=" + stat[0] + Const.NEW_LINE_SEPARATOR + "tps=" + stat[1] + Const.NEW_LINE_SEPARATOR + "kB_read/s=" + stat[2] + Const.NEW_LINE_SEPARATOR + "kB_wrtn/s=" + stat[3] + Const.NEW_LINE_SEPARATOR + "kB_dscd/s=" + stat[4] + Const.NEW_LINE_SEPARATOR + "kB_read=" + stat[5] + Const.NEW_LINE_SEPARATOR + "kB_wrtn=" + stat[6] + Const.NEW_LINE_SEPARATOR + "kB_dscd=" + stat[7] + Const.NEW_LINE_SEPARATOR);

                            writer.newLine();

                            writer.flush();

                        }

                        default -> System.err.println("Error in writing file in switch case");
                    }

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

