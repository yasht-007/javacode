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


    void poll(String userName, String hostName, String password, int port)
    {
        Channel channel = null;

        OutputStream outputStream = null;

        PrintStream commander = null;

        try
        {
            jSch = new JSch();

            config.put("StrictHostKeyChecking", "no");

            if (jSch != null)
            {
                session = jSch.getSession(userName, hostName, port);

                if (session != null)
                {
                    session.setPassword(password);

                    session.setConfig(config);

                    session.connect();

                    System.out.println(session.isConnected());
                }

                System.out.println("Connected to the Remote PC ");

                channel = session.openChannel("exec");

                ((ChannelExec) channel).setCommand("mpstat -P ALL; free");

                channel.connect();

                channel.setInputStream(null);

                ((ChannelExec) channel).setErrStream(System.err);

                InputStream in = channel.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String output;

                while ((output = reader.readLine()) != null)
                {
                    // Nikunj write code here...
                    System.out.println(output);
                }

                channel.disconnect();


//                channel = session.openChannel("shell");
//
//                outputStream = channel.getOutputStream();
//
//                commander = new PrintStream(outputStream, true);
//
//                channel.setOutputStream(System.out, true);
//
//                channel.connect();
//
//                commander.println("ls");

            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            if (channel != null)
            {
                channel.disconnect();

            }

            if (session != null)
            {
                session.disconnect();
            }
        }
    }

}

