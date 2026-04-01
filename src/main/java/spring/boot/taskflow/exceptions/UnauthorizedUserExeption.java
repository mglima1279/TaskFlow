package spring.boot.taskflow.exceptions;

public class UnauthorizedUserExeption extends RuntimeException {
    public UnauthorizedUserExeption() {
        super("TASK DOES NOT BELONG TO THIS USER");
    }

    public UnauthorizedUserExeption(String message) {
        super(message);
    }
}
