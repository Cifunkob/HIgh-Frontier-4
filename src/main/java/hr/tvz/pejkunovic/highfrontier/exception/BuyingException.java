package hr.tvz.pejkunovic.highfrontier.exception;

public class BuyingException extends RuntimeException {
    public BuyingException() {
    }

    public BuyingException(String message) {
        super(message);
    }

    public BuyingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuyingException(Throwable cause) {
        super(cause);
    }

    public BuyingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
