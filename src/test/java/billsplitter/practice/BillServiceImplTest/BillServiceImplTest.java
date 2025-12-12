package billsplitter.practice.BillServiceImplTest;

import billsplitter.practice.dto.PersonCostDto;
import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;
import billsplitter.practice.entity.Bill;
import billsplitter.practice.entity.Person;
import billsplitter.practice.mapper.BillMapper;
import billsplitter.practice.service.impl.BillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BillServiceImplTest {

    private BillMapper billMapper;
    private BillServiceImpl billService;

    @BeforeEach
    void setUp() {
        billMapper = Mockito.mock(BillMapper.class);
        billService = new BillServiceImpl(billMapper);
    }

    @Test
    void split_shouldCalculateCommissionAndTotalCorrectly() {

        //INPUT DTO
        BillRequestDto request = new BillRequestDto();
        request.setTotalCost(BigDecimal.valueOf(100000));
        request.setCommissionPercent(BigDecimal.TEN); // 10%

        PersonCostDto p1 = new PersonCostDto();
        p1.setName("Ali");
        p1.setCost(BigDecimal.valueOf(30000));

        PersonCostDto p2 = new PersonCostDto();
        p2.setName("Vali");
        p2.setCost(BigDecimal.valueOf(20000));

        request.setPersons(List.of(p1, p2));

        //ENTITY
        Bill bill = new Bill();
        bill.setTotalCost(BigDecimal.valueOf(100000));
        bill.setCommissionPercent(BigDecimal.TEN);
        bill.setPersons(List.of(
                new Person("Ali", BigDecimal.valueOf(30000)),
                new Person("Vali", BigDecimal.valueOf(20000))
        ));

        //MapStruct stub
        when(billMapper.toEntity(request)).thenReturn(bill);

        BillResponseDto responseDto = new BillResponseDto();
        responseDto.setPersons(request.getPersons());

        when(billMapper.toResponse(bill)).thenReturn(responseDto);

        //TEST
        BillResponseDto response = billService.split(request);

        //Expected
        BigDecimal expectedCommission = BigDecimal.valueOf(10000);   // 10% from 100000
        BigDecimal expectedTotalWithCommission = BigDecimal.valueOf(110000);

        assertThat(response).isNotNull();
        assertThat(response.getCommission()).isEqualByComparingTo(expectedCommission);
        assertThat(response.getTotalWithCommission())
                .isEqualByComparingTo(expectedTotalWithCommission);

        verify(billMapper, times(1)).toEntity(request);
        verify(billMapper, times(1)).toResponse(bill);
    }
}
