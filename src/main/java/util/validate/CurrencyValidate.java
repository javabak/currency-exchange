package util.validate;

import java.util.regex.Pattern;

public class CurrencyValidate {

    public static boolean validateId(int id) {
        String regex = "^\\d+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(String.valueOf(id).trim()).matches();
    }

    public static boolean validateRate(double rate) {
        String regex = "^\\d+.\\d+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(String.valueOf(rate).trim()).matches();
    }


    public static boolean validateCode(String code) {
        String regex = "^[A-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(code).find();
    }

    public static boolean validateName(String name) {
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(name).matches();
    }
}
