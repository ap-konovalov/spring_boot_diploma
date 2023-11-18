package ru.netology.moneytransferservice.repositories;

import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;

public interface MonetTransferRepository {
    String transferMoney(MoneyTransferRequestDto requestDto);
    String confirmOperation(ConfirmOperationRequestDto requestDto);
}
