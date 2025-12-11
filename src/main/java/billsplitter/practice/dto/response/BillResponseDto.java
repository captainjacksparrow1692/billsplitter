package billsplitter.practice.dto.response;

import billsplitter.practice.dto.PersonCostDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class BillResponseDto {
    private List<PersonCostDto> people;
    private BigDecimal totalBill;
}
