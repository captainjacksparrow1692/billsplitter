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

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private static final BigDecimal COMMISSION_PERCENT = BigDecimal.valueOf(10);

    private final BillMapper billMapper;

    @Override
    public BillResponseDto split(BillRequestDto request) {

        Bill bill = billMapper.toEntity(request);
        bill.setCommissionPercent(COMMISSION_PERCENT);

        validateBill(bill);

        BigDecimal commission = calculateCommission(bill.getTotalCost());
        BigDecimal totalWithCommission = bill.getTotalCost().add(commission);

        BigDecimal totalPersonsCost = bill.getPersons().stream()
                .map(p -> p.getCost() == null ? BigDecimal.ZERO : p.getCost())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<PersonCostDto> persons = bill.getPersons().stream()
                .map(p -> calculatePersonCost(p.getName(), p.getCost(), totalPersonsCost, commission))
                .toList();

        return new BillResponseDto(
                totalWithCommission.setScale(2, RoundingMode.HALF_UP),
                commission.setScale(2, RoundingMode.HALF_UP),
                persons
        );
    }

    //helpers

    private void validateBill(Bill bill) {
        if (bill.getTotalCost() == null || bill.getTotalCost().signum() <= 0) {
            throw new IllegalArgumentException("Total cost must be greater than zero");
        }
        if (bill.getPersons() == null || bill.getPersons().isEmpty()) {
            throw new IllegalArgumentException("At least one person is required");
        }
    }

    private BigDecimal calculateCommission(BigDecimal totalCost) {
        return totalCost
                .multiply(COMMISSION_PERCENT)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private PersonCostDto calculatePersonCost(
            String name,
            BigDecimal cost,
            BigDecimal totalPersonsCost,
            BigDecimal commission
    ) {
        BigDecimal safeCost = cost == null ? BigDecimal.ZERO : cost;

        BigDecimal ratio = safeCost.divide(totalPersonsCost, 4, RoundingMode.HALF_UP);

        BigDecimal result = safeCost
                .add(commission.multiply(ratio))
                .setScale(2, RoundingMode.HALF_UP);

        return new PersonCostDto(name, result);
    }
}
