package ru.netology.moneytransferservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.entitites.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.entitites.MoneyTransferRequestDto;
import ru.netology.moneytransferservice.entitites.OperationResponseDto;
import ru.netology.moneytransferservice.services.MoneyTransferService;

@RestController
public class MoneyTransferController {

    @Autowired
    MoneyTransferService moneyTransferService;

    @PostMapping("/transfer")
    public ResponseEntity<OperationResponseDto> transfer(@RequestBody @Valid MoneyTransferRequestDto requestDto) {
        String transferId = moneyTransferService.transferMoney(requestDto);
        return ResponseEntity.ok(OperationResponseDto.builder().operationId(transferId).build());
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationResponseDto> confirmOperation(@RequestBody @Valid ConfirmOperationRequestDto requestDto) {
        String operationId = moneyTransferService.confirmOperation(requestDto);
        return ResponseEntity.ok(OperationResponseDto.builder().operationId(operationId).build());
    }

}
