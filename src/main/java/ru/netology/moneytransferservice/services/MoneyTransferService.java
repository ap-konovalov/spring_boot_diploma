package ru.netology.moneytransferservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.entitites.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.entitites.MoneyTransferRequestDto;
import ru.netology.moneytransferservice.repositories.MonetTransferRepositoryImpl;

@Service
public class MoneyTransferService {

    @Autowired
    MonetTransferRepositoryImpl monetTransferRepository;

    public String transferMoney(MoneyTransferRequestDto requestDto) {
        return monetTransferRepository.transferMoney(requestDto);
    }

    public String confirmOperation(ConfirmOperationRequestDto requestDto) {
        return monetTransferRepository.confirmOperation(requestDto);
    }
}
