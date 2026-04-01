package spring.boot.taskflow.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("TASK NOT FOUND");
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
