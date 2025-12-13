package billsplitter.practice.BillServiceImplTest;

import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;
import billsplitter.practice.entity.Bill;
import billsplitter.practice.entity.Person;
import billsplitter.practice.mapper.BillMapper;
import billsplitter.practice.service.impl.BillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BillServiceImplTest {

    private BillMapper billMapper;
    private BillServiceImpl billService;

    @BeforeEach
    void setUp() {
        billMapper = mock(BillMapper.class);
        billService = new BillServiceImpl(billMapper);
    }

    @Test
    void split_shouldCalculateCommissionAndPersonsCostCorrectly() {

        //INPUT DTO
        BillRequestDto request = new BillRequestDto();
        request.setTotalCost(BigDecimal.valueOf(100_000));

        //ENTITY
        Bill bill = new Bill();
        bill.setTotalCost(BigDecimal.valueOf(100_000));
        bill.setPersons(List.of(
                new Person("Ali", BigDecimal.valueOf(30_000)),
                new Person("Vali", BigDecimal.valueOf(20_000))
        ));

        when(billMapper.toEntity(request)).thenReturn(bill);

        //TEST
        BillResponseDto response = billService.split(request);

        //EXPECTED
        BigDecimal expectedCommission = BigDecimal.valueOf(10_000); // 10%
        BigDecimal expectedTotal = BigDecimal.valueOf(110_000);

        // Ali: 30k / 50k = 60% → 6000 commission → 36000
        BigDecimal expectedAli = BigDecimal.valueOf(36_000);

        // Vali: 20k / 50k = 40% → 4000 commission → 24000
        BigDecimal expectedVali = BigDecimal.valueOf(24_000);

        //ASSERT
        assertThat(response).isNotNull();
        assertThat(response.getCommission()).isEqualByComparingTo(expectedCommission);
        assertThat(response.getTotalCost()).isEqualByComparingTo(expectedTotal);

        assertThat(response.getPersons()).hasSize(2);

        assertThat(response.getPersons())
                .anyMatch(p -> p.getName().equals("Ali")
                        && p.getCost().compareTo(expectedAli) == 0);

        assertThat(response.getPersons())
                .anyMatch(p -> p.getName().equals("Vali")
                        && p.getCost().compareTo(expectedVali) == 0);

        verify(billMapper, times(1)).toEntity(request);
        verifyNoMoreInteractions(billMapper);
    }
}
