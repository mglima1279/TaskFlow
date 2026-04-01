package spring.boot.taskflow.exceptions;

public class ExistingUserException extends RuntimeException {
    public ExistingUserException() {
        super("EXISTING USER");
    }

    public ExistingUserException(String message) {
        super(message);
    }
}
