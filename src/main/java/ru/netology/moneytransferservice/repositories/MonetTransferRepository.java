package ru.netology.moneytransferservice.repositories;

import ru.netology.moneytransferservice.entitites.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.entitites.MoneyTransferRequestDto;

public interface MonetTransferRepository {
    String transferMoney(MoneyTransferRequestDto requestDto);
    String confirmOperation(ConfirmOperationRequestDto requestDto);
}
