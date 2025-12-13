package billsplitter.practice.service.impl;

import billsplitter.practice.dto.PersonCostDto;
import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;
import billsplitter.practice.entity.Bill;
import billsplitter.practice.mapper.BillMapper;
import billsplitter.practice.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillMapper billMapper;

    private static final BigDecimal FIXED_COMMISSION_PERCENT = BigDecimal.valueOf(10);

    @Override
    public BillResponseDto split(BillRequestDto request) {

        Bill bill = billMapper.toEntity(request);
        bill.setCommissionPercent(FIXED_COMMISSION_PERCENT);

        if (bill.getTotalCost() == null || bill.getTotalCost().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total cost must be greater than zero");
        }

        if (bill.getPersons() == null || bill.getPersons().isEmpty()) {
            throw new IllegalArgumentException("At least one person is required");
        }

        BigDecimal commissionValue = bill.getTotalCost()
                .multiply(bill.getCommissionPercent())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal totalCost = bill.getTotalCost().add(commissionValue);

        BigDecimal totalPersonsCost = bill.getPersons().stream()
                .map(person -> person.getCost() != null ? person.getCost() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<PersonCostDto> persons = bill.getPersons().stream()
                .map(person -> {
                    BigDecimal personCost = person.getCost() != null
                            ? person.getCost()
                            : BigDecimal.ZERO;
                    BigDecimal ratio = personCost.divide(totalPersonsCost, 4, RoundingMode.HALF_UP);
                    BigDecimal personCostWithCommission = personCost
                            .add(commissionValue.multiply(ratio))
                            .setScale(2, RoundingMode.HALF_UP);
                    return new PersonCostDto(person.getName(), personCostWithCommission);
                })
                .collect(Collectors.toList());

        BillResponseDto response = new BillResponseDto();
        response.setTotalCost(totalCost.setScale(2, RoundingMode.HALF_UP));
        response.setCommission(commissionValue.setScale(2, RoundingMode.HALF_UP));
        response.setPersons(persons);

        return response;
    }
}
