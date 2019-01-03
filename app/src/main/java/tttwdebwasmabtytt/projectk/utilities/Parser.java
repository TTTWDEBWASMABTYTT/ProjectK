package tttwdebwasmabtytt.projectk.utilities;

/**
 * This class holds the method for parsing strings to extract commands from them
 */

public class Parser {

    //String Values.
    public final static String UNMUTE = "unmute";
    public final static String UNKNOWN = "unknown";
    public final static String BAD_CREDENTIALS = "bad";

    /**
     * Returns true if the given string starts with the temporary password.
     * Returns false otherwise.
     * @param msg The string to be parsed
     * @return Whether the string starts with the password or not.
     */
    public static String parseString(String msg){
        //TODO Temporary password should be changed and an actual password used instead. (Store password in a file if shared preferences can be deleted by user).
        // Temporary password
        String tempPassword = "12345";
        if(msg.startsWith(tempPassword))
        {
            String command = msg.substring(tempPassword.length(),msg.length());
            if(command.equals(UNMUTE)){
                return UNMUTE;
            } else {
                return UNKNOWN;
            }
        }else {
            return BAD_CREDENTIALS;
        }
    }

}
