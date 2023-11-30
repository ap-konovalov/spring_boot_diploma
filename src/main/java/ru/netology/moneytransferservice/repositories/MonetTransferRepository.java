package ru.netology.moneytransferservice.repositories;

import ru.netology.moneytransferservice.exceptions.NoSuchTransactionException;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;

import java.util.UUID;

public interface MonetTransferRepository {
    void transferMoney(MoneyTransferRequestDto requestDto, UUID operationId);
    void confirmOperation(String operationId) throws NoSuchTransactionException;
}
