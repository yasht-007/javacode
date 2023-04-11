package Utility;

public class Const
{
    public static final String NEW_LINE_SEPARATOR = "\n";
    public static final String GREEN_COLOUR = "\u001B[32m";
    public static final String RED_COLOUR = "\033[31m";
    public static final String RESET_COLOUR = "\u001B[0m";
    public static final String NAME_REGEX = "^[a-zA-Z]+(\\s[a-zA-Z]+){0,2}$";
    public static final String DOB_REGEX = "^(0[1-9]|1[0-2])\\/(0[1-9]|[12][0-9]|3[01])\\/(19|20)\\d{2}$";
    public static final String CONTACT_REGEX = "^[6-9]\\d{9}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PASS_REGEX = "[0-9a-zA-Z@#$%]{8,}";
    public static final String CUSTOMER_ID_REGEX = "^\\d{4}$";
    public static final String ACCOUNT_NUMBER_REGEX = "^\\d{7}$";
    public static final String AMOUNT_REGEX = "^-?\\d{1,19}$";
    public static final int NO_OF_THREADS = 10;
    public static final String CUSTOMER_ID_ERROR_MESSAGE = "Please enter valid customer id";
    public static final String PASS_ERROR_MESSAGE = "Please enter valid password";
    public static final String NAME_ERROR_MESSAGE = "Please enter valid name";
    public static final String DOB_ERROR_MESSAGE = "Please enter valid date of birth";
    public static final String CONTACT_ERROR_MESSAGE = "Please enter valid contact number";
    public static final String EMAIL_ERROR_MESSAGE = "Please enter valid email address";
    public static final String ACCOUNT_TYPE_ERROR_MESSAGE = "Please enter only savings or current";
    public static final String ACCOUNT_NUMBER_ERROR_MESSAGE = "Please enter valid account number";
    public static final String ACCOUNT_NOT_FOUND_ERROR_MESSAGE = "Your entered account number doesn't exist";
    public static final String AMOUNT_ERROR_MESSAGE = "Please enter valid amount";
    public static final String INSUFFICIENT_AMOUNT_ERROR_MESSAGE = "You don't have sufficient balance to perform this operation";
    public static final String CUSTOMER_ID_INPUT_MESSAGE = "Enter Customer Id (Must be of 4 digits) : ";
    public static final String PASSWORD_INPUT_MESSAGE = "Enter Password (Must be of at least 8 digits) : ";
    public static final String NAME_INPUT_MESSAGE = "Enter your full name : ";
    public static final String DOB_INPUT_MESSAGE = "Enter your date of birth (MM/DD/YYYY) : ";
    public static final String CONTACT_INPUT_MESSAGE = "Enter your contact number (Must be of 10 digits) : ";
    public static final String EMAIL_INPUT_MESSAGE = "Enter your email address : ";
    public static final String ACCOUNT_TYPE_INPUT_MESSAGE = "Enter type of account (savings/current) : ";
    public static final String ACCOUNT_NUMBER_INPUT_MESSAGE = "Enter account number to whom you want to transfer (7 digits) : ";
    public static final String AMOUNT_TRANSFER_INPUT_MESSAGE = "Enter amount to transfer : ";
    public static final String AMOUNT_DEPOSIT_INPUT_MESSAGE = "Enter amount to deposit : ";
    public static final String AMOUNT_WITHDRAW_INPUT_MESSAGE = "Enter amount to withdraw : ";

}
