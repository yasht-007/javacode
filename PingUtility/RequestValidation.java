package PingUtility;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RequestValidation
{
    static boolean isIPValid(String ip)
    {

        return RegexChecker.complileAndMatchRegex(FormatUtility.IP_REGEX, ip);

    }

    static boolean checkUnique(String discoveredName, String ip, Map<String, String> profiles)
    {

        return !profiles.containsKey(discoveredName) && !profiles.containsValue(ip);

    }
    private abstract static class RegexChecker
    {
        public static Pattern pattern;
        public static Matcher matcher;

        // Method to check valid ip address
        private static boolean complileAndMatchRegex(String regexPattern, String input)
        {

            pattern = Pattern.compile(regexPattern);

            matcher = pattern.matcher(input);

            return matcher.matches();
        }

    }

}