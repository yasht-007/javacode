package utility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BuildProcess
{
   public static boolean discover(String ip)
    {
        BufferedReader inputReader = null;

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
                inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                output = inputReader.readLine();

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
                if (inputReader != null)
                {
                    inputReader.close();
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

    public boolean poll(String ip, String discoveryName, String dateTime)
    {
        Process process = null;

        BufferedWriter outputWriter = null;

        BufferedReader outputReader = null;

        try
        {

            int exitCode;

            List<String> command = new ArrayList<>();

            command.add("fping");

            command.add("-c");

            command.add("3");

            command.add("-q");

            command.add(ip);

            var processBuilder = new ProcessBuilder(command);

            processBuilder.redirectErrorStream(true);

            process = processBuilder.start();

            exitCode = process.waitFor();

            if (exitCode == 0)
            {

                outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                outputWriter = new BufferedWriter(new FileWriter(FormatUtility.POLLING_FILE_PATH + discoveryName + "- poll.log/" + "availability.log", true));

                outputWriter.write(dateTime + " : " + outputReader.readLine());

                outputWriter.newLine();

                outputWriter.flush();

                return true;

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

        return false;

    }

}
