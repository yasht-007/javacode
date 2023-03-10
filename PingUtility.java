import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingUtility
{
    static final String greenColor = "\u001B[32m"; // ANSI escape code for green color
    static final String resetColor = "\u001B[0m"; // ANSI escape code to reset color to default

    private static boolean buildProcessAndGetOutput(String discoveryName, String ipAddresses)
    {
        BufferedReader stdInputReader = null;

        Process process = null;

        String pingOutput;

        boolean pingFlag = false;

        try
        {

            System.out.println("\nPlease wait, we are going to ping " + ipAddresses + " ......\n");

            var processBuilder = new ProcessBuilder(new ArrayList<>(Arrays.asList("bash", "-c", "fping -c 3 -q " + ipAddresses)));

            processBuilder.redirectErrorStream(true);

            process = processBuilder.start();

            if (process.waitFor() == 0)
            {
                stdInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                pingOutput = stdInputReader.readLine();

                for (int index = 0; index < pingOutput.split("\n").length; index++)
                {

                    String[] filteredResult = pingOutput.split("\n")[index].split(":")[1].split("=")[1].split("/");

                    if (filteredResult[0].trim().equals(filteredResult[1].trim()) && filteredResult[2].substring(0, filteredResult[2].indexOf("%")).equals("0"))
                    {

                        pingFlag = true;

                        System.out.println(discoveryName + " : " + pingOutput.split("\n")[index].split(":")[0].trim() + " is Up (Active)");

                    }
                    else
                    {

                        System.out.println(pingOutput.split("\n")[index].split(":")[0].trim() + " is Down (Inactive)");

                    }

                }

            }
            else
            {

                System.err.println("Process abnormally terminated");

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

        return pingFlag;

    }

    private static void displayOptions()
    {

        System.out.println("\nPlease enter a choice to proceed : \n");

        System.out.println("1.Discovery\n");

        System.out.print("Enter your choice here : ");

    }

    private static void startPolling(Map<String, String> provisionProfiles)
    {

        Process process = null;

        File outputFile;

        BufferedWriter outputWriter = null;

        BufferedReader outputReader = null;

        try
        {

            var processBuilder = new ProcessBuilder(new ArrayList<>(Arrays.asList("bash", "-c", "fping -c 3 -q " + String.join(" ", provisionProfiles.keySet()))));

            processBuilder.redirectErrorStream(true);

            process = processBuilder.start();

            if (process.waitFor() == 0)
            {

                outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String output;

                while ((output = outputReader.readLine()) != null)
                {
                    outputFile = new File("/home/yash/PingOutputs/" + provisionProfiles.get(output.split(":")[0].trim()) + " - " + output.split(":")[0].trim() + " provisioning.txt");

                    if (!(outputFile.exists()))
                    {

                        outputFile.createNewFile();

                    }

                    outputWriter = new BufferedWriter(new FileWriter(outputFile, true));

                    outputWriter.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " : " + output);

                    outputWriter.newLine();

                    outputWriter.flush();

                }

            }

            else
            {

                System.out.println("Process abnormally terminated");

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

                    outputReader.close();

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

    private static boolean isValidIpAddress(String ipAddress)
    {

        String regex = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(ipAddress);

        if (matcher.matches())
        {

            return true;

        }
        else
        {

            System.err.println("\nPlease enter valid Ip Address\n");

            return false;

        }
    }

    public static void main(String[] args)
    {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        BufferedReader reader = null;

        BufferedWriter writer = null;

        Map<String, String> profiles = new HashMap<>();

        Map<String, String> provisionProfiles = new LinkedHashMap<>();

        boolean pingFlag;

        try
        {

            reader = new BufferedReader(new InputStreamReader(System.in));

            while (true)
            {

                displayOptions();

                var choice = Integer.parseInt(reader.readLine());

                if (choice == 1)
                {

                    String discoveryName;

                    String ipAddress;

                    System.out.print("\nPlease enter unique discovery name : ");

                    discoveryName = reader.readLine();

                    System.out.print("\nPlease enter IP Address that you want to ping : ");

                    ipAddress = reader.readLine();

                    if (!isValidIpAddress(ipAddress))
                    {

                        continue;

                    }

                    if (profiles.putIfAbsent(discoveryName.toLowerCase().trim(), ipAddress.trim()) != null)
                    {

                        System.err.println("\nDiscovery name or Ip Address already exist. Please provide unique Discovery name and Ip address");

                        System.out.println("Press enter to continue : ");

                    }
                    else
                    {

                        pingFlag = buildProcessAndGetOutput(discoveryName, ipAddress);

                        if (pingFlag)
                        {

                            String provisioningOption;

                            System.out.print("\nDo you want to start provisioning? (yes/no) : ");

                            provisioningOption = reader.readLine();

                            if (provisioningOption.toLowerCase().trim().equals("yes"))
                            {
                                provisionProfiles.put(ipAddress, discoveryName);

                                System.out.println("\n" + greenColor + "Your provisioning task for " + ipAddress + " has been successfully assigned" + resetColor);

                                executorService.scheduleAtFixedRate(() -> startPolling(provisionProfiles), 0, 5, TimeUnit.SECONDS);

                            }

                        }
                        else
                        {

                            System.err.println("\n" + ipAddress + " is unreachable\n");

                        }

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

                //   executor.shutdown();

                if (reader != null)
                {

                    reader.close();

                }

                if (writer != null)
                {

                    writer.close();

                }

            }
            catch (Exception exception)
            {

                exception.printStackTrace();

            }

        }

    }

}
