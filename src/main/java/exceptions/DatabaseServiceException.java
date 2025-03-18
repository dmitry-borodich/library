package exceptions;

public class DatabaseServiceException extends RuntimeException {

    public DatabaseServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
