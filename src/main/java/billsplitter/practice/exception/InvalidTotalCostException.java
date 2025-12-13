package billsplitter.practice.exception;

public class InvalidTotalCostException extends RuntimeException {
    public InvalidTotalCostException() {
        super("Total cost must be greater than zero");
    }
}
