package exceptions.validate_exceptions;

public class WrongParametersException extends RuntimeException {
    public WrongParametersException(String s) {
        super(s);
    }
}
