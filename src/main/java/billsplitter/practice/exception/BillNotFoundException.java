package billsplitter.practice.exception;

public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException() {
        super();
    }

    public BillNotFoundException(String message) {
        super(message);
    }
}

