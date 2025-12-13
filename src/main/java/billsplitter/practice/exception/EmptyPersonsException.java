package billsplitter.practice.exception;

public class EmptyPersonsException extends RuntimeException {
    public EmptyPersonsException() {
        super("At least one person is required");
    }
}
