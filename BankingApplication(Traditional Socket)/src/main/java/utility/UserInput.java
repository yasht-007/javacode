package utility;

import java.io.BufferedReader;

public class UserInput
{
    public static String getInput(String regex, String inputMessage, String errorMessage, BufferedReader reader)
    {
        String input = null;

        try
        {
            System.out.print(inputMessage);

            input = reader.readLine().trim();

            if (Validator.isEmpty(input) || !Validator.validatePattern(regex, input))
            {
                System.out.println(Const.RED_COLOUR + errorMessage + Const.RESET_COLOUR);

                return getInput(regex, inputMessage, errorMessage, reader);
            }

            return input;
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return input;
    }

    public static String getAccountType(String inputMessage, String errorMessage, BufferedReader reader)
    {
        String input = null;

        try
        {
            System.out.print(inputMessage);

            input = reader.readLine().trim();

            if (Validator.isEmpty(input) || !Validator.validateAccountType(input))
            {
                System.out.println(Const.RED_COLOUR + errorMessage + Const.RESET_COLOUR);

                return getAccountType( inputMessage, errorMessage, reader);
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
