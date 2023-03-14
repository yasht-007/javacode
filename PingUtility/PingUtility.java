package PingUtility;

import java.io.*;
import java.util.*;

public class PingUtility
{
    private static boolean buildProcess(String ip)
    {
        BufferedReader stdInputReader = null;

        Process process = null;

        String output;

        try
        {

            System.out.println(FormatUtility.NEW_LINE_SEPARATOR + "Please wait, we are going to ping " + ip + " ......" + FormatUtility.NEW_LINE_SEPARATOR);

            List<String> command = new ArrayList<>();

            command.add("fping");

            command.add("-c");

            command.add("3");

            command.add("-q");

            command.add(ip);

            var processBuilder = new ProcessBuilder(command);

            processBuilder.redirectErrorStream(true);

            process = processBuilder.start();

            if (process.waitFor() == 0)
            {
                stdInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                output = stdInputReader.readLine();

                var splitWithLine = output.split(FormatUtility.NEW_LINE_SEPARATOR);

                for (String s : splitWithLine)
                {
                    String[] filteredResult = s.split(":")[1].split("=")[1].split("/");

                    return filteredResult[0].trim().equals(filteredResult[1].trim()) && filteredResult[2].substring(0, filteredResult[2].indexOf("%")).equals("0");
                }

            }

            else
            {
                return false;
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
                if (stdInputReader != null)
                {
                    stdInputReader.close();
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

        return false;

    }

    public static void main(String[] args)
    {
        Timer timer = null;

        BufferedReader reader = null;

        Map<String, String> profiles = new HashMap<>();

        Map<String, String> credentialProfiles = new HashMap<>();

        try
        {
            timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask()
            {
                @Override
                public void run()
                {
                    Poller.startPolling(credentialProfiles);
                }

            }, 0, 5000);

            reader = new BufferedReader(new InputStreamReader(System.in));

            while (true)
            {
                System.out.println(FormatUtility.NEW_LINE_SEPARATOR + "Please enter a choice to proceed : " + FormatUtility.NEW_LINE_SEPARATOR);

                System.out.println("1.Discovery" + FormatUtility.NEW_LINE_SEPARATOR);

                System.out.print("Enter your choice here : ");

                var choice = reader.readLine();

                if (choice.equals("1"))
                {
                    String discoveredName;

                    String ip;

                    System.out.print(FormatUtility.NEW_LINE_SEPARATOR + "Please enter unique discovery name : ");

                    discoveredName = reader.readLine();

                    System.out.print(FormatUtility.NEW_LINE_SEPARATOR + "Please enter unique IP Address that you want to ping : ");

                    ip = reader.readLine();

                    if (!RequestValidation.isIPValid(ip))
                    {
                        System.err.println(FormatUtility.NEW_LINE_SEPARATOR + "Please enter valid Ip Address" + FormatUtility.NEW_LINE_SEPARATOR);

                        continue;
                    }

                    if (!(RequestValidation.checkUnique(discoveredName.trim(), ip.trim(), profiles)))
                    {
                        System.err.println(FormatUtility.NEW_LINE_SEPARATOR + "Discovery name or Ip Address already exist. Please provide unique Discovery name and Ip address");

                        continue;
                    }

                    profiles.put(discoveredName.trim(), ip.trim());

                    if (buildProcess(ip))
                    {
                        System.out.println(discoveredName + " : " + ip + " is Up (Active)");

                        String provisioningOption;

                        System.out.print(FormatUtility.NEW_LINE_SEPARATOR + "Do you want to start provisioning? (yes/no) : ");

                        provisioningOption = reader.readLine();

                        if (provisioningOption.toLowerCase().trim().equals("yes"))
                        {
                            credentialProfiles.put(ip, discoveredName);

                            System.out.println(FormatUtility.NEW_LINE_SEPARATOR + FormatUtility.GREEN_COLOUR + "Your provisioning task for " + ip + " has been successfully assigned" + FormatUtility.RESET_COLOUR);
                        }

                    }
                    else
                    {
                        System.out.println(discoveredName + " : " + ip + " is Down (Inactive)");
                    }

                }
                else
                {
                    System.err.println("Please enter valid choice");
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
                if (timer != null)
                {
                    timer.cancel();
                }

                if (reader != null)
                {
                    reader.close();
                }

            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }

    }

}
