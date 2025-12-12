package billsplitter.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonCostDto {

    private String name;

    // Сколько заказал данный человек
    private BigDecimal cost;
}
