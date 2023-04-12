package Utility;

public class Const
{
    public static final String NEW_LINE_SEPARATOR = "\n";
    public static final String TAB_SEPARATOR = "\t";
    public static final String DOT_SEPARATOR = ".";
    public static final String HOST = "localhost";
    public static final int PORT_NUMBER = 9999;
    public static final String GREEN_COLOUR = "\u001B[32m";
    public static final String RED_COLOUR = "\033[31m";
    public static final String RESET_COLOUR = "\u001B[0m";
    public static final String NAME_REGEX = "^[a-zA-Z]+(\\s[a-zA-Z]+){0,2}$";
    public static final String ADDRESS_REGEX = "^.{0,50}$";
    public static final String CONTACT_REGEX = "^[6-9]\\d{9}$";
    public static final String PASS_REGEX = "[0-9a-zA-Z@#$%]{8,}";
    public static final String DIGITS_REGEX = "^\\d+$";
    public static final String YES_OR_NO_REGEX = "^(?i)(yes|no)$";
    public static final int NO_OF_THREADS = 10;
    public static final String PASS_ERROR_MESSAGE = "Please enter valid password";
    public static final String NAME_ERROR_MESSAGE = "Please enter valid name";
    public static final String CONTACT_ERROR_MESSAGE = "Please enter valid contact number";
    public static final String ADDRESS_ERROR_MESSAGE = "Please enter valid address";
    public static final String ORDER_ERROR_MESSAGE = "Please enter valid choice";
    public static final String INVALID_CHOICE_ERROR_MESSAGE = "Please enter valid choice";
    public static final String PASSWORD_INPUT_MESSAGE = "Enter Password (Must be of at least 8 digits) : ";
    public static final String NAME_INPUT_MESSAGE = "Enter your full name : ";
    public static final String CONTACT_INPUT_MESSAGE = "Enter your contact number (Must be of 10 digits) : ";
    public static final String ORDER_INPUT_MESSAGE = "Do you want to order these items (Yes/No) : ";
    public static final String ADDRESS_INPUT_MESSAGE = "Enter your Address (Atmost 50 characters) : ";

}
