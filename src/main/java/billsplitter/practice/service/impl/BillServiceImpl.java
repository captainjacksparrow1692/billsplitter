package billsplitter.practice.service.impl;

import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;
import billsplitter.practice.entity.Bill;
import billsplitter.practice.mapper.BillMapper;
import billsplitter.practice.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillMapper billMapper;

    @Override
    public BillResponseDto split(BillRequestDto request) {

        // 1) DTO → Entity
        Bill bill = billMapper.toEntity(request);

        // 2) Вычисление комиссии в валюте
        BigDecimal commissionValue = bill.getTotalCost()
                .multiply(bill.getCommissionPercent())
                .divide(BigDecimal.valueOf(100));

        // 3) Общая сумма с учетом комиссии
        BigDecimal CommissionPercent = bill.getTotalCost().add(commissionValue);
        bill.setCommissionPercent(CommissionPercent);

        // 4) Entity → Response
        BillResponseDto response = billMapper.toResponse(bill);

        // 5) Устанавливаем поле "commission" вручную
        response.setCommission(commissionValue);

        // 6) Возвращаем ответ
        return response;
    }
}
