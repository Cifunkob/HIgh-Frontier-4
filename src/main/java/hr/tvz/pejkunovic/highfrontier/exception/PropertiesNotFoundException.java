package hr.tvz.pejkunovic.highfrontier.exception;

public class PropertiesNotFoundException extends RuntimeException {
    public PropertiesNotFoundException() {
    }

    public PropertiesNotFoundException(String message) {
        super(message);
    }

    public PropertiesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesNotFoundException(Throwable cause) {
        super(cause);
    }

    public PropertiesNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
