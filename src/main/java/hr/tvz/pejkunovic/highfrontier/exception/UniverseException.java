package hr.tvz.pejkunovic.highfrontier.exception;

public class UniverseException extends RuntimeException {
    public UniverseException() {
    }

    public UniverseException(String message) {
        super(message);
    }

    public UniverseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniverseException(Throwable cause) {
        super(cause);
    }

    public UniverseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
