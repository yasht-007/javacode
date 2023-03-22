import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bootstrap
{
    static int index = -1;

    public static void main(String[] args)
    {

//        Timer timer = null;

        BufferedReader reader = null;

        BuildProcess process;

        List<CredentialProfile> credentialProfile = new ArrayList<>();

        Map<String, String> credentialProfiles = new HashMap<>();

        ExecutorService executor = Executors.newCachedThreadPool();

        try
        {
//            timer = new Timer();
//
//            timer.scheduleAtFixedRate(new TimerTask()
//            {
//                @Override
//                public void run()
//                {
//                    Poller.startPolling(credentialProfile);
//                }
//
//            }, 0, 5000);

            reader = new BufferedReader(new InputStreamReader(System.in));

            while (true)
            {
                System.out.println(FormatUtility.NEW_LINE_SEPARATOR + "Please enter a choice to proceed : " + FormatUtility.NEW_LINE_SEPARATOR);

                System.out.println("1.Discovery" + FormatUtility.NEW_LINE_SEPARATOR);

                System.out.print("Enter your choice here : ");

                var choice = reader.readLine();

                if (choice.equals("1"))
                {
                    String discoveryName;

                    String ip;

                    String userName;

                    String password;

                    int port;

                    System.out.print(FormatUtility.NEW_LINE_SEPARATOR + "Please enter unique discovery name : ");

                    discoveryName = reader.readLine().trim();

                    System.out.print(FormatUtility.NEW_LINE_SEPARATOR + "Please enter unique IP Address that you want to ping : ");

                    ip = reader.readLine().trim();

                    System.out.print(FormatUtility.NEW_LINE_SEPARATOR + "Please enter username of your device : ");

                    userName = reader.readLine().trim();

                    System.out.print(FormatUtility.NEW_LINE_SEPARATOR + "Please enter password of your device : ");

                    password = reader.readLine();

                    System.out.print(FormatUtility.NEW_LINE_SEPARATOR + "Please enter port number of in which ssh service is running : ");

                    port = Integer.parseInt(reader.readLine());

                    if (!RequestValidation.isIPValid(ip))
                    {
                        System.err.println(FormatUtility.NEW_LINE_SEPARATOR + "Please enter valid Ip Address" + FormatUtility.NEW_LINE_SEPARATOR);

                        continue;
                    }

                    if (!(RequestValidation.checkUnique(discoveryName, ip, credentialProfiles)))
                    {
                        System.err.println(FormatUtility.NEW_LINE_SEPARATOR + "Discovery name or Ip Address already exist. Please provide unique Discovery name and Ip address");

                        continue;
                    }

                    process = new BuildProcess();

                    SSHClient client = new SSHClient();

                    if (process.discover(ip) && client.discover(userName, ip, password, port))
                    {
                        System.out.println(discoveryName + " : " + ip + " is Up (Active)");

                        String provisioningOption;

                        System.out.print(FormatUtility.NEW_LINE_SEPARATOR + "Do you want to start provisioning? (yes/no) : ");

                        provisioningOption = reader.readLine();

                        if (provisioningOption.toLowerCase().trim().equals("yes"))
                        {
                            credentialProfile.add(new CredentialProfile(discoveryName, ip, userName, password, port));

                            System.out.println(FormatUtility.NEW_LINE_SEPARATOR + FormatUtility.GREEN_COLOUR + "Your provisioning task for " + ip + " has been successfully assigned" + FormatUtility.RESET_COLOUR);

                            File file = new File(FormatUtility.POLLING_FILE_PATH + discoveryName + "- poll.log");

                            if (!file.exists() && !file.mkdir())
                            {
                                System.err.println("Couldn't make file directory for " + discoveryName);

                                continue;
                            }

                            executor.execute(() -> {

                                int profileIndex = ++index;

                                long startTime = System.currentTimeMillis();

                                while (true)
                                {
                                    long currentTime = System.currentTimeMillis();

                                    if ((currentTime - startTime) >= 20000)
                                    {
                                        startTime = currentTime;

                                        new Poller().startPolling(credentialProfile, profileIndex);
                                    }

                                }
                            });
                        }

                    }
                    else
                    {
                        System.out.println(discoveryName + " : " + ip + " is Down (Inactive)");
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
//                if (timer != null)
//                {
//                    timer.cancel();
//                }

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
