package billsplitter.practice.service.impl;

import billsplitter.practice.dto.PersonCostDto;
import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;
import billsplitter.practice.entity.Bill;
import billsplitter.practice.exception.*;
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

    private static final BigDecimal COMMISSION_PERCENT = BigDecimal.valueOf(10);

    private final BillMapper billMapper;

    @Override
    public BillResponseDto split(BillRequestDto request) {

        Bill bill = billMapper.toEntity(request);

        //ВАЛИДАЦИЯ
        validateBill(bill);

        //Сумма всех персон
        BigDecimal totalPersonsCost = bill.getPersons().stream()
                .map(p -> {
                    if (p.getCost() == null || p.getCost().signum() < 0) {
                        throw new InvalidPersonCostException(p.getName());
                    }
                    return p.getCost();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPersonsCost.signum() == 0) {
            throw new ZeroTotalPersonsCostException();
        }

        //Проверка общей суммы
        if (bill.getTotalCost().compareTo(totalPersonsCost) != 0) {
            throw new IncorrectTotalCostException(totalPersonsCost, bill.getTotalCost());
        }

        //Расчет комиссии
        BigDecimal commission = calculateCommission(bill.getTotalCost());
        BigDecimal totalWithCommission = bill.getTotalCost().add(commission);

        //Расчет стоимости на каждого
        List<PersonCostDto> persons = bill.getPersons().stream()
                .map(p -> calculatePersonCost(p.getName(), p.getCost(), totalPersonsCost, commission))
                .collect(Collectors.toList());

        //Формирование ответа
        return new BillResponseDto(
                totalWithCommission.setScale(2, RoundingMode.HALF_UP),
                commission.setScale(2, RoundingMode.HALF_UP),
                persons
        );
    }

    //HELPERS

    private void validateBill(Bill bill) {
        if (bill.getTotalCost() == null || bill.getTotalCost().signum() <= 0) {
            throw new InvalidTotalCostException();
        }
        if (bill.getPersons() == null || bill.getPersons().isEmpty()) {
            throw new EmptyPersonsException();
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
        BigDecimal ratio = cost.divide(totalPersonsCost, 4, RoundingMode.HALF_UP);
        BigDecimal costWithCommission = cost
                .add(commission.multiply(ratio))
                .setScale(2, RoundingMode.HALF_UP);
        return new PersonCostDto(name, costWithCommission);
    }
}
