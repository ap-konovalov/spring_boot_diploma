package ru.netology.moneytransferservice.repositories;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.entitites.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.entitites.MoneyTransferRequestDto;

import java.util.UUID;

@Repository
public class MonetTransferRepositoryImpl implements MonetTransferRepository {

    public MonetTransferRepositoryImpl() {
    }

    @Override
    public String transferMoney(MoneyTransferRequestDto requestDto) {
        return UUID.randomUUID().toString();
    }

    @Override
    public String confirmOperation(ConfirmOperationRequestDto requestDto) {
        return requestDto.getOperationId();
    }
}
