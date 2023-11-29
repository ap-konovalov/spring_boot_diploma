package ru.netology.moneytransferservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;
import ru.netology.moneytransferservice.repositories.MonetTransferRepositoryImpl;

@Service
@RequiredArgsConstructor
public class MoneyTransferServiceImpl implements MoneyTransferService {

    private final MonetTransferRepositoryImpl monetTransferRepository;

    public String transferMoney(MoneyTransferRequestDto requestDto) {
        return monetTransferRepository.transferMoney(requestDto);
    }

    public String confirmOperation(ConfirmOperationRequestDto requestDto) {
        return monetTransferRepository.confirmOperation(requestDto);
    }
}
