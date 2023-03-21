class FormatUtility
{
    // Code for inserting new line
    public static final String NEW_LINE_SEPARATOR = "\n";

    // ANSI escape code for green color
    public static final String GREEN_COLOUR = "\u001B[32m";

    // ANSI escape code to reset color to default
    public static final String RESET_COLOUR = "\u001B[0m";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static final String POLLING_FILE_PATH = "../../PingOutputs/";

    static String IP_REGEX = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";


}
