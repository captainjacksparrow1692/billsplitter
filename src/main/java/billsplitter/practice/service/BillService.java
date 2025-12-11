package billsplitter.practice.service;


import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public interface BillService {
    BillResponseDto calculateBill(@RequestBody @Validated BillRequestDto request);


    static BillResponseDto create(BillRequestDto requestDto) {
        return null;
    }

    default BillResponseDto split(BillRequestDto request) {
        return  null;
    }
}
