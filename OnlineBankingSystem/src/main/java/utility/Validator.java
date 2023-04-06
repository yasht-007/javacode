package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator
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

}
