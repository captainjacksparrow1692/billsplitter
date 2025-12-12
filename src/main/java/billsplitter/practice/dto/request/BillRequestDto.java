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

    // Общая сумма без комиссии
    private BigDecimal totalCost;

    // Комиссия в процентах (например: 10 = 10%)
    private BigDecimal commissionPercent;

    // Список людей и их расходы
    private List<PersonCostDto> persons;
}
