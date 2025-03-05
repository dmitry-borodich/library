package exceptions;

import database.DatabaseSetup;

public class DatabaseSetupException extends RuntimeException {
    public DatabaseSetupException(String message, Throwable cause) {
        super(message, cause);
    }
}
