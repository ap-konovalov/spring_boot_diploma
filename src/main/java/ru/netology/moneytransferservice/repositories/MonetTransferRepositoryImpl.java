package ru.netology.moneytransferservice.repositories;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;

import java.util.UUID;

@Repository
@NoArgsConstructor
public class MonetTransferRepositoryImpl implements MonetTransferRepository {

    @Override
    public String transferMoney(MoneyTransferRequestDto requestDto) {
        return UUID.randomUUID().toString();
    }

    @Override
    public String confirmOperation(ConfirmOperationRequestDto requestDto) {
        return requestDto.getOperationId();
    }
}
