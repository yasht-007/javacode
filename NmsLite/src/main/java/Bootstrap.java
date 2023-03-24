import api.CredentialProfile;
import api.User;
import nms.Discovery;
import nms.MetricScheduler;
import utility.FormatUtility;
import utility.RequestValidation;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bootstrap
{
    public static void main(String[] args)
    {

        BufferedReader reader = null;

        List<CredentialProfile> credentialProfile = new ArrayList<>();

        ExecutorService executor = Executors.newCachedThreadPool();

        try
        {

            reader = new BufferedReader(new InputStreamReader(System.in));

            while (true)
            {

                System.out.println(FormatUtility.NEW_LINE_SEPARATOR + "Please enter a choice to proceed : " + FormatUtility.NEW_LINE_SEPARATOR);

                System.out.println("1.Monitor" + FormatUtility.NEW_LINE_SEPARATOR);

                System.out.println("2.Exit" + FormatUtility.NEW_LINE_SEPARATOR);

                System.out.print("Enter your choice here : ");

                var choice = reader.readLine();

                if (choice.equals("1"))
                {
                    User user = new User();

                    user.getUserInput();

                    if (!RequestValidation.isIPValid(user.getIp()))
                    {
                        System.err.println(FormatUtility.NEW_LINE_SEPARATOR + "Please enter valid Ip Address" + FormatUtility.NEW_LINE_SEPARATOR);

                        continue;
                    }

                    if (Discovery.ping(user) && Discovery.ssh(user))
                    {
                        System.out.println(user.getDiscoveryName() + " : " + user.getIp() + " is Up (Active)");

                        String provisioningOption;

                        System.out.print(FormatUtility.NEW_LINE_SEPARATOR + "Do you want to start provisioning? (yes/no) : ");

                        provisioningOption = reader.readLine();

                        if (provisioningOption.toLowerCase().trim().equals("yes"))
                        {
                            CredentialProfile userProfile = new CredentialProfile(user);

                            credentialProfile.add(userProfile);

                            System.out.println(FormatUtility.NEW_LINE_SEPARATOR + FormatUtility.GREEN_COLOUR + "Your provisioning task for " + user.getIp() + " has been successfully assigned" + FormatUtility.RESET_COLOUR);

                            File file = new File(FormatUtility.POLLING_FILE_PATH + user.getDiscoveryName() + "- poll.log");

                            if (!file.exists() && !file.mkdir())
                            {
                                System.err.println("Couldn't make file directory for " + user.getDiscoveryName());
                            }

                            new MetricScheduler().scheduleTask(executor, 20000L, userProfile);

                        }

                    }

                    else
                    {
                        System.out.println(user.getDiscoveryName() + " : " + user.getIp() + " is Down (Inactive)");
                    }

                }

                else if (choice.equals("2"))
                {
                    System.exit(0);
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
