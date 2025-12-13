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

    // Общая сумма с учётом комиссии
    private BigDecimal totalCost;

    // Сумма комиссии в валюте (например: 12.50)
    private BigDecimal commission;

    // Расчёт по каждому человеку
    private List<PersonCostDto> persons;
}
