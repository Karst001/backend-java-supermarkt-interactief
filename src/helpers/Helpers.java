package helpers;

public class Helpers {

    public static boolean isInt(String inputValue) {
        //test if inputValue is a string or numeric, can be used globally
        if (inputValue == null) {
            return false;
        }

        try {
            Integer.parseInt(inputValue.trim());
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }

    public static boolean isDouble(String inputValue) {
        if (inputValue == null) {
            return false;
        }

        try {
            Double.parseDouble(inputValue.trim());
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }
}
