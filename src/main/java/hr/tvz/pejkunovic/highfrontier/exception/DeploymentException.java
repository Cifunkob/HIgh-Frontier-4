package hr.tvz.pejkunovic.highfrontier.exception;

public class DeploymentException extends RuntimeException {
    public DeploymentException() {
    }

    public DeploymentException(String message) {
        super(message);
    }

    public DeploymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeploymentException(Throwable cause) {
        super(cause);
    }

    public DeploymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
