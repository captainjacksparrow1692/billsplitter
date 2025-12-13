package billsplitter.practice.dto.request;

import billsplitter.practice.dto.PersonCostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillRequestDto {

    private BigDecimal totalCost;

    private BigDecimal commissionPercent;

    private List<PersonCostDto> persons;
}
