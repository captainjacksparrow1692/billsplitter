package billsplitter.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private List<Person> persons;
    private BigDecimal sharedCost;
    private BigDecimal totalCost;
    private BigDecimal commissionPercent;

}
