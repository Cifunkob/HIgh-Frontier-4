package hr.tvz.pejkunovic.highfrontier.exception;

public class CalculationException extends RuntimeException {
    public CalculationException() {
    }

    public CalculationException(String message) {
        super(message);
    }

    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculationException(Throwable cause) {
        super(cause);
    }

    public CalculationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
