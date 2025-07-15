package hr.tvz.pejkunovic.highfrontier.exception;

public class MovementException extends RuntimeException {
    public MovementException() {
    }

    public MovementException(String message) {
        super(message);
    }

    public MovementException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovementException(Throwable cause) {
        super(cause);
    }

    public MovementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
