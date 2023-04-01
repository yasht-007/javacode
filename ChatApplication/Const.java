package ChatApplication;

import java.util.concurrent.atomic.AtomicBoolean;

public class Const
{
    public static final int PORT_NO = 5555;
    public static final String LOCALHOST = "localhost";
    public static final String HOST = "10.20.40.158";
    public static final String CLIENT = "Client";
    public static final String SERVER = "Server";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\033[33m";
    public static final String NEW_LINE_SEPERATOR = "\n";
    public static AtomicBoolean END_CONNECTION = new AtomicBoolean(false);

}
