import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingUtility {

    static final String greenColor = "\u001B[32m"; // ANSI escape code for green color
    static final String resetColor = "\u001B[0m"; // ANSI escape code to reset color to default

    private static boolean buildProcessAndGetOutput(String discoveryName, String ipAddresses) {

        BufferedReader stdInputReader = null;

        Process process = null;

        String pingOutput;

        boolean pingFlag = false;

        try {

            System.out.println("\nPlease wait, we are going to ping " + ipAddresses + " ......\n");

            var processBuilder = new ProcessBuilder(new ArrayList<>(Arrays.asList("bash", "-c", "fping -c 3 -q " + ipAddresses)));

            processBuilder.redirectErrorStream(true);

            process = processBuilder.start();

            if (process.waitFor() == 0) {

                stdInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                pingOutput = stdInputReader.readLine();

                for (int index = 0; index < pingOutput.split("\n").length; index++) {

                    String[] filteredResult = pingOutput.split("\n")[index].split(":")[1].split("=")[1].split("/");

                    if (filteredResult[0].trim().equals(filteredResult[1].trim()) && filteredResult[2].substring(0, filteredResult[2].indexOf("%")).equals("0")) {

                        pingFlag = true;

                        System.out.println(discoveryName + " : " + pingOutput.split("\n")[index].split(":")[0].trim() + " is Up (Active)");

                    } else {

                        System.out.println(pingOutput.split("\n")[index].split(":")[0].trim() + " is Down (Inactive)");

                    }

                }

            } else {

                System.err.println("Process abnormally terminated");

            }

        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {

            try {

                if (stdInputReader != null) stdInputReader.close();

                if (process != null) process.destroy();

            } catch (Exception exception) {

                exception.printStackTrace();

            }

        }

        return pingFlag;

    }

    private static void displayOptions() {

        System.out.println("\nPlease enter a choice to proceed : \n");

        System.out.println("1.Discovery\n");

        System.out.print("Enter your choice here : ");

    }

    private static boolean isNameUnique(String discoveryName) {

        boolean uniqueFlag = true;

        Scanner scanner = null;

        try {

            scanner = new Scanner(new FileReader("/home/yash/PingOutputs/Discovery.txt"));

            while (scanner.hasNext()) {

                if (scanner.nextLine().trim().equals(discoveryName)) {

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

    private static void startPolling(String ipAddress, String discoveryName) {

        Process process = null;

        File outputFile;

        BufferedWriter outputWriter = null;

        BufferedReader outputReader = null;

        while (true) {

            try {

                outputFile = new File("/home/yash/PingOutputs/" + discoveryName + " - " + ipAddress + " provisioning.txt");

                if (!(outputFile.exists())) {

                    outputFile.createNewFile();

                }

                var processBuilder = new ProcessBuilder(new ArrayList<>(Arrays.asList("bash", "-c", "fping -c 3 -q " + ipAddress)));

                processBuilder.redirectErrorStream(true);

                process = processBuilder.start();

                if (process.waitFor() == 0) {

                    outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    outputWriter = new BufferedWriter(new FileWriter(outputFile, true));

                    outputWriter.write(System.currentTimeMillis() + " : " + outputReader.readLine());

                    outputWriter.newLine();

                    outputWriter.flush();

                    Thread.sleep(5000);

                } else {

                    System.out.println("Process abnormally terminated");

                }

            } catch (Exception exception) {

                exception.printStackTrace();

            } finally {

                try {

                    if (process != null) {

                        process.destroy();

                    }

                    if (outputReader != null) {

                        outputReader.close();

                    }

                    if (outputWriter != null) {

                        outputReader.close();

                    }

                } catch (Exception exception) {

                    exception.printStackTrace();

                }

            }
        }

    }

    private static boolean isValidIpAddress(String ipAddress) {

        String regex = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(ipAddress);

        if (matcher.matches()) {

            return true;

        } else {

            System.err.println("\nPlease enter valid Ip Address\n");

            return false;

        }
    }

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        BufferedReader reader = null;

        BufferedWriter writer = null;

        boolean pingFlag;

        try {

            reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                displayOptions();

                var choice = Integer.parseInt(reader.readLine());

                if (choice == 1) {

                    String discoveryName, ipAddress;

                    System.out.print("\nPlease enter unique discovery name : ");

                    discoveryName = reader.readLine();

                    System.out.print("\nPlease enter IP Address that you want to ping : ");

                    ipAddress = reader.readLine();

                    if (!isValidIpAddress(ipAddress)) {

                        continue;

                    }

                    if (!(isNameUnique(discoveryName))) {

                        System.err.println("\nDiscovery name or Ip Address already exist. Please provide unique Discovery name and Ip address");

                        System.out.println("Press enter to continue : ");

                    } else {

                        writer = new BufferedWriter(new FileWriter("/home/yash/PingOutputs/Discovery.txt", true));

                        writer.write(discoveryName.toLowerCase().trim() + "\n" + ipAddress.trim() + "\n");

                        writer.newLine();

                        writer.flush();

                        pingFlag = buildProcessAndGetOutput(discoveryName, ipAddress);

                        if (pingFlag) {

                            String provisioningOption;

                            System.out.print("\nDo you want to start provisioning? (yes/no) : ");

                            provisioningOption = reader.readLine();

                            if (provisioningOption.toLowerCase().trim().equals("no")) {

                                System.out.println("Press enter to continue : ");

                                reader.readLine();

                            } else if (provisioningOption.toLowerCase().trim().equals("yes")) {

                                System.out.println("\n" + greenColor + "Your provisioning task for " + ipAddress + " has been successfully assigned" + resetColor);

                                executor.execute(() -> startPolling(ipAddress, discoveryName));

                            }

                        } else {

                            System.err.println("\n" + ipAddress + " is unreachable\n");

                        }

                    }

                } else {

                    System.err.println("Please enter valid choice");

                }

            }


        } catch (Exception exception) {

            exception.printStackTrace();

        } finally {

            try {

                executor.shutdown();

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
