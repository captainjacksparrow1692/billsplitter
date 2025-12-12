package billsplitter.practice.controller;

import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;
import billsplitter.practice.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bill")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping("/split")
    public ResponseEntity<BillResponseDto> split(@RequestBody BillRequestDto request) {
        return ResponseEntity.ok(billService.split(request));
    }
}
