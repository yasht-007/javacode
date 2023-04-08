package utility;

import java.util.concurrent.atomic.AtomicBoolean;

public class Const
{
    public static final String NEW_LINE_SEPARATOR = "\n";
    public static final String NAME_REGEX = "^[a-zA-Z]+(\\s[a-zA-Z]+){0,2}$";
    public static final String DOB_REGEX = "^(0[1-9]|1[0-2])\\/(0[1-9]|[12][0-9]|3[01])\\/(19|20)\\d{2}$";
    public static final String CONTACT_REGEX = "^[6-9]\\d{9}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PASS_REGEX = "[0-9a-zA-Z@#$%]{8,}";
    public static final String CUST_ID_REGEX = "^\\d{4}$";
    public static final int NO_OF_THREADS = 10;

}
