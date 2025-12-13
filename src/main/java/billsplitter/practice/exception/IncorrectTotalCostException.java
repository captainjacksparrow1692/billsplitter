package billsplitter.practice.exception;

import java.math.BigDecimal;

public class IncorrectTotalCostException extends InvalidBillException {

    public IncorrectTotalCostException(BigDecimal expected, BigDecimal actual) {
        super("Incorrect total cost: expected " + expected + " but got " + actual);
    }
}

