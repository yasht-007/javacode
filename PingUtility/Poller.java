package PingUtility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Poller
{
    static Process process = null;

    static BufferedWriter outputWriter = null;

    static BufferedReader outputReader = null;

    static void startPolling(Map<String, String> credentialProfile)
    {
        if (credentialProfile.size() != 0)
        {
            int exitCode;

            try
            {
                List<String> command = new ArrayList<>();

                command.add("fping");

                command.add("-c");

                command.add("3");

                command.add("-q");

                command.addAll(credentialProfile.keySet());

                var processBuilder = new ProcessBuilder(command);

                processBuilder.redirectErrorStream(true);

                process = processBuilder.start();

                exitCode = process.waitFor();

                if (exitCode == 0)
                {

                    outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    String output;

                    while ((output = outputReader.readLine()) != null)
                    {

                        outputWriter = new BufferedWriter(new FileWriter(FormatUtility.POLLING_FILE_PATH + credentialProfile.get(output.split(":")[0].trim()) + " - " + " poll.log", true));

                        outputWriter.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern(FormatUtility.DATE_TIME_FORMAT)) + " : " + output);

                        outputWriter.newLine();

                        outputWriter.flush();

                    }

                }

                else
                {
                    System.err.println("Process exited with status code " + exitCode);
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
                        outputWriter.close();
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

    }

}