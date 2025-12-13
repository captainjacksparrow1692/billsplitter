package billsplitter.practice.exception;

public class InvalidBillException extends RuntimeException {
    public InvalidBillException() {
        super();
    }

    public InvalidBillException(String message) {
        super(message);
    }
}
