package tttwdebwasmabtytt.projectk.utilities;

/**
 * This class holds the method for parsing strings to extract commands from them
 */

public class Parser {

    // Temporary password
    private String tempPassword = "123";


    /**
     * Returns true if the given string starts with the temporary password.
     * Returns false otherwise.
     * @param string The string to be parsed
     * @return Whether the string starts with the password or not.
     */
    public boolean parseString(String string){

        int passwordLength = tempPassword.length();

        if(string.length() >= passwordLength){
            return string.substring(0, passwordLength).equals(tempPassword);
        } else {
            return false;
        }

    }

}
