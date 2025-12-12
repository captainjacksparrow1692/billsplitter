package billsplitter.practice.service;


import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;

public interface BillService {
    BillResponseDto split(BillRequestDto request);
}
