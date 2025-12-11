package billsplitter.practice.dto.request;

import billsplitter.practice.dto.PersonCostDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BillRequestDto {

    @NotEmpty(message = "People list must not be empty")
    private List<PersonCostDto> people;

    @NotNull(message = "Shared cost is required")
    @DecimalMin(value = "0.0", message = "Shared cost must be >= 0")
    private BigDecimal sharedCost;

    @NotNull(message = "Commission persent is required")
    @DecimalMin(value = "0.0", message = "Commission must be >= 0")
    private BigDecimal commissionPersent;
}
