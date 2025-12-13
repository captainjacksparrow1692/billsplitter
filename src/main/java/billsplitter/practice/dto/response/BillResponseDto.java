package billsplitter.practice.dto.response;

import billsplitter.practice.dto.PersonCostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillResponseDto {

    private BigDecimal totalCost;

    private BigDecimal commission;

    private List<PersonCostDto> persons;
}
