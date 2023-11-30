package ru.netology.moneytransferservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exceptions.NoSuchTransactionException;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;
import ru.netology.moneytransferservice.repositories.MonetTransferRepositoryImpl;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MoneyTransferServiceImpl implements MoneyTransferService {

    private final MonetTransferRepositoryImpl monetTransferRepository;

    public String transferMoney(MoneyTransferRequestDto requestDto) {
        UUID operationId = UUID.randomUUID();
        monetTransferRepository.transferMoney(requestDto, operationId);
        return operationId.toString();
    }

    public String confirmOperation(ConfirmOperationRequestDto requestDto) throws NoSuchTransactionException {
        String operationId = requestDto.getOperationId();
        monetTransferRepository.confirmOperation(operationId);
        return operationId;
    }
}
