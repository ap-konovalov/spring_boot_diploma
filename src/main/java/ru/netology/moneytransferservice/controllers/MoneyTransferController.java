package ru.netology.moneytransferservice.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;
import ru.netology.moneytransferservice.model.OperationResponseDto;
import ru.netology.moneytransferservice.services.MoneyTransferService;

@RestController
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;

    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<OperationResponseDto> transfer(@RequestBody @Valid MoneyTransferRequestDto requestDto) {
        String transferId = moneyTransferService.transferMoney(requestDto);
        return ResponseEntity.ok(getOperationResponse(transferId));
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationResponseDto> confirmOperation(@RequestBody @Valid ConfirmOperationRequestDto requestDto) {
        String operationId = moneyTransferService.confirmOperation(requestDto);
        return ResponseEntity.ok(getOperationResponse(operationId));
    }

    private static OperationResponseDto getOperationResponse(String operationId) {
        return OperationResponseDto.builder().operationId(operationId).build();
    }
}
