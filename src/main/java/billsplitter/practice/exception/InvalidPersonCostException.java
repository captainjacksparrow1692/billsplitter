package billsplitter.practice.exception;

public class InvalidPersonCostException extends InvalidBillException  {
    public InvalidPersonCostException(String name) {
        super("Invalid cost for person: " + name);
    }
}
