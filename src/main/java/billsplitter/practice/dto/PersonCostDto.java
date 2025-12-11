package billsplitter.practice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PersonCostDto {
    @NotBlank(message = "Person name must not be empty")
    private String name;

    @NotNull(message = "Personal cost is required")
    @DecimalMin(value ="0.0", message = "Personal cost must be > 0")
    private BigDecimal personalCost;

    //вычисление
    private BigDecimal finalCost;
}
