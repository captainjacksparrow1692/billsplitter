package billsplitter.practice.exception;

public class ZeroTotalPersonsCostException extends InvalidBillException {
    public ZeroTotalPersonsCostException() {
        super("Total cost must be greater than zero");
    }
}
