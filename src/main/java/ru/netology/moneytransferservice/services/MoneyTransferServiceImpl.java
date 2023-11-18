package ru.netology.moneytransferservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;
import ru.netology.moneytransferservice.repositories.MonetTransferRepositoryImpl;

@Service
public class MoneyTransferServiceImpl implements MoneyTransferService {

    @Autowired
    MonetTransferRepositoryImpl monetTransferRepository;

    public String transferMoney(MoneyTransferRequestDto requestDto) {
        return monetTransferRepository.transferMoney(requestDto);
    }

    public String confirmOperation(ConfirmOperationRequestDto requestDto) {
        return monetTransferRepository.confirmOperation(requestDto);
    }
}
