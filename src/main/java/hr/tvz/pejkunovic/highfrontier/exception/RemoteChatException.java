package hr.tvz.pejkunovic.highfrontier.exception;

public class RemoteChatException extends RuntimeException {
    public RemoteChatException() {
    }

    public RemoteChatException(String message) {
        super(message);
    }

    public RemoteChatException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteChatException(Throwable cause) {
        super(cause);
    }

    public RemoteChatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
