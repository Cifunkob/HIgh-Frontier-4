package hr.tvz.pejkunovic.highfrontier.exception;

public class DocumentationGenerationException extends RuntimeException {
    public DocumentationGenerationException() {
    }

    public DocumentationGenerationException(String message) {
        super(message);
    }

    public DocumentationGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentationGenerationException(Throwable cause) {
        super(cause);
    }

    public DocumentationGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
