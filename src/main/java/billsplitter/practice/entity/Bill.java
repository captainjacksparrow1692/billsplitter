package billsplitter.practice.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Bill {
    private List<Person> people;
    private BigDecimal sharedCost;
    private BigDecimal totalCost;
    private BigDecimal commissionPerson;
}
