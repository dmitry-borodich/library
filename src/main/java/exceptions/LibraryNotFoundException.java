package exceptions;

public class LibraryNotFoundException extends Exception {
    public LibraryNotFoundException(String message) {
        super(message);
    }
}
