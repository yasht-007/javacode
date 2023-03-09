import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PingUtility {
    private static boolean buildProcessAndGetOutput(String ipAddresses) {

        BufferedReader stdInputReader = null;

        Process pingProcess = null;

        String pingOutput;

        boolean pingFlag = false;

        try {

            System.out.println("\nPlease wait, we are going to ping " + ipAddresses + " ......\n");

            var processBuilder = new ProcessBuilder(new ArrayList<>(Arrays.asList("bash", "-c", "fping -c 3 -q " + ipAddresses)));

            processBuilder.redirectErrorStream(true);

            pingProcess = processBuilder.start();

            stdInputReader = new BufferedReader(new InputStreamReader(pingProcess.getInputStream()));

            pingOutput = stdInputReader.readLine();

            for (int index = 0; index < pingOutput.split("\n").length; index++) {

                String[] filteredResult = pingOutput.split("\n")[index].split(":")[1].split("=")[1].split("/");

                if (filteredResult[0].trim().equals(filteredResult[1].trim()) && filteredResult[2].substring(0, filteredResult[2].indexOf("%")).equals("0")) {

                    pingFlag = true;

                    System.out.println(pingOutput.split("\n")[index].split(":")[0].trim() + " is Up (Active)");

                } else {

                    System.out.println(pingOutput.split("\n")[index].split(":")[0].trim() + " is Down (Inactive)");

                }

            }

        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {

            try {

                if (stdInputReader != null) stdInputReader.close();

                if (pingProcess != null) pingProcess.destroy();

            } catch (IOException exception) {

                exception.printStackTrace();

            }

        }

        return pingFlag;

    }

    private static int getChoice(BufferedReader reader) {

        var choice = 0;

        try {

            choice = Integer.parseInt(reader.readLine());

        } catch (Exception exception) {

            exception.printStackTrace();

        }

        return choice;

    }

    private static void displayOptions() {

        System.out.println("\nPlease enter a choice to proceed : \n");

        System.out.println("1.Discovery");

        System.out.println("2.Provisioning\n");

        System.out.println("Enter your choice here : ");

    }

    private static boolean areUnique(String discoveryName, String ipAddress) {

        boolean uniqueFlag = true;

        Scanner scanner = null;

        try {

            scanner = new Scanner(new FileReader("/home/yash/PingOutputs/Discovery.txt"));

            while (scanner.hasNext()) {

                if (scanner.nextLine().trim().equals(discoveryName) || scanner.nextLine().trim().equals(ipAddress)) {

                    uniqueFlag = false;

                    break;
                }

            }

        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {

            try {

                if (scanner != null)

                    scanner.close();

            } catch (Exception exception) {

                exception.printStackTrace();

            }

        }

        return uniqueFlag;
    }

    public static void main(String[] args) {

        BufferedReader reader = null;

        BufferedWriter writer = null;

        boolean pingFlag;

        try {

            reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                displayOptions();

                if (getChoice(reader) == 1) {

                    String discoveryName, ipAddress;

                    System.out.println("Please enter unique discovery name : ");

                    discoveryName = reader.readLine();

                    System.out.println("Please enter IP Address that you want to ping : ");

                    ipAddress = reader.readLine();

                    if (!(areUnique(discoveryName, ipAddress))) {

                        System.err.println("\nDiscovery name or Ip Address already exist. Please provide unique Discovery name and Ip address");
                        System.out.println("Press enter to continue : ");
                        reader.readLine();

                    } else {

                        writer = new BufferedWriter(new FileWriter("/home/yash/PingOutputs/Discovery.txt", true));

                        writer.write(discoveryName.toLowerCase().trim() + "\n" + ipAddress.trim() + "\n");

                        writer.newLine();

                        writer.flush();

                        // Provisioning implementation(to be continued ...)
                        pingFlag = buildProcessAndGetOutput(ipAddress);

                        if (pingFlag) {

                            String provisioningOption;

                            System.out.println("Do you want to start provisioning? (yes/no)");

                            provisioningOption = reader.readLine();

                            if (provisioningOption.toLowerCase().trim().equals("no")) {

                                System.err.println("\nDiscovery name or Ip Address already exist. Please provide unique Discovery name and Ip address");

                                System.out.println("Press enter to continue : ");

                                reader.readLine();

                            } else if (provisioningOption.toLowerCase().trim().equals("yes")) {

                                String provisioningInterval;

                                System.out.println("Enter provisioning interval in minutes : ");

                                provisioningInterval = reader.readLine();

                            }
                        }

                    }

                } else {

                    // provisioning choice (Implementation remaining)

                }

            }

        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {

            try {

                if (reader != null) {

                    reader.close();

                }

                if (writer != null) {

                    writer.close();

                }

            } catch (Exception exception) {

                exception.printStackTrace();

            }

        }

    }

}
