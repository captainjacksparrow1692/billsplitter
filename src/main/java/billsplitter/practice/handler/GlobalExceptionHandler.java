package billsplitter.practice.handler;

import billsplitter.practice.dto.ErrorDto.ErrorDto;
import billsplitter.practice.exception.BillNotFoundException;
import billsplitter.practice.exception.IncorrectTotalCostException;
import billsplitter.practice.exception.InvalidBillException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidBillException.class)
    public ResponseEntity<ErrorDto> handleInvalidBill(InvalidBillException ex) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                "INVALID_BILL",
                ex.getMessage()
        );
    }

    @ExceptionHandler(BillNotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFound(BillNotFoundException ex) {
        return buildError(
                HttpStatus.NOT_FOUND,
                "BILL_NOT_FOUND",
                ex.getMessage()
        );
    }

    @ExceptionHandler(IncorrectTotalCostException.class)
    public ResponseEntity<ErrorDto> handleIncorrectTotalCost(IncorrectTotalCostException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "INCORRECT_TOTAL_COST", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleOther(Exception ex) {
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Unexpected error occurred"
        );
    }

    private ResponseEntity<ErrorDto> buildError(
            HttpStatus status,
            String code,
            String message
    ) {
        return ResponseEntity
                .status(status)
                .body(new ErrorDto(code, message, status.value()));
    }
}
