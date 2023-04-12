package Utility;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInput
{
    public static Pattern pattern;
    public static Matcher matcher;

    public static boolean validatePattern(String regexPattern, String input)
    {

        pattern = Pattern.compile(regexPattern);

        matcher = pattern.matcher(input);

        return matcher.matches();
    }

    public static boolean isEmpty(String field)
    {
        return field == null || field.equalsIgnoreCase("") || field.equalsIgnoreCase(" ");
    }

    public static String validateInput(String regex, String inputMessage, String errorMessage, BufferedReader reader)
    {
        String input = null;

        try
        {
            System.out.print(inputMessage);

            input = reader.readLine().trim();

            if (isEmpty(input) || !validatePattern(regex, input))
            {
                System.out.println(Const.RED_COLOUR + errorMessage + Const.RESET_COLOUR);

                return validateInput(regex, inputMessage, errorMessage, reader);
            }

            return input;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return input;
    }
}
