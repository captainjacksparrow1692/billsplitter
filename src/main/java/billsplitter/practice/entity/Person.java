package billsplitter.practice.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Person {
    private String name;
    private BigDecimal personalCost;
    private BigDecimal finalCost;
}
