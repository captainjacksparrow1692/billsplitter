package billsplitter.practice.service.impl;

import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;
import billsplitter.practice.service.BillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.ap.internal.util.RoundContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BillServiceImpl implements BillService {

    @Override
    public BillResponseDto split(BillRequestDto request) {
        return null;
    }

    @Override
    public BillResponseDto calculateBill(BillRequestDto request) {
        BigDecimal commission = request.getCommissionPersent()
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

        List<PersonCostDto> people = request.getSharedCost();

        //считаем индивидуальные расходы
        BigDecimal totalPersonalCost = people.stream()
                .map(PersonCostDto::getPersonalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //считаем общее блюдо
        BigDecimal sharedCost = request.getSharedCost();

        //разделение sharedcost поровну
        BigDecimal sharedPerPerson = sharedCost
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

        //комиссия для каждого
        for (PersonDto p : people) {
            BigDecimal total = p.getPersonalCost().add(sharedPerPerson);
            BigDecimal totalCommission = total.add(total.multiply(commission));

            p.setFinalCost(totalWithCommission.setScale(2, RoundingMode.HALF_UP));
        }

        //total
        BigDecimal totalBill = totalPersonalCost
                .add(sharedCost)
                .add(totalPersonalCost.add(sharedCost).multiply(commission))
                .setScale(2, RoundingMode.HALF_UP);

        return new BillResponseDto(people, totalBill);
    }
}
