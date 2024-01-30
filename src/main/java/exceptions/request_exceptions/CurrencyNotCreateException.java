package exceptions.request_exceptions;

public class CurrencyNotCreateException extends RuntimeException {
    public CurrencyNotCreateException(String message) {
        super(message);
    }
}
