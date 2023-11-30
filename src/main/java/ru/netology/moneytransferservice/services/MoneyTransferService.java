package ru.netology.moneytransferservice.services;

import ru.netology.moneytransferservice.exceptions.NoSuchTransactionException;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;

public interface MoneyTransferService {

    String transferMoney(MoneyTransferRequestDto requestDto);

    String confirmOperation(ConfirmOperationRequestDto requestDto) throws NoSuchTransactionException;
}
