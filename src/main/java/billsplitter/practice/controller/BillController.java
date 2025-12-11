package billsplitter.practice.controller;

import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;
import billsplitter.practice.service.BillService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/billsplitter")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BillController {
    BillService billService;

    @PostMapping("/split")
    public ResponseEntity<BillResponseDto> create(@RequestBody BillRequestDto requestDto){
        return ResponseEntity.ok(BillService.create(requestDto));
    }

    @PostMapping
    public ResponseEntity<BillResponseDto> calculate(@RequestBody @Validated BillRequestDto request) {
        return ResponseEntity.ok(billService.calculateBill(request));
    }
}
