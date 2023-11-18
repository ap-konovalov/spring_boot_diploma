package ru.netology.moneytransferservice.repositories;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;

import java.util.UUID;

import static java.lang.String.format;

@Repository
@NoArgsConstructor
@Slf4j
public class MonetTransferRepositoryImpl implements MonetTransferRepository {

    @Override
    public String transferMoney(MoneyTransferRequestDto requestDto) {
        String operationId = UUID.randomUUID().toString();
        log.info(format("Transfer money operation from card: %s to card: %s created. Amount: %s. OperationId: %s",
                requestDto.getCardFromNumber(), requestDto.getCardToNumber(), requestDto.getAmount().getValue(),
                operationId));
        return operationId;
    }

    @Override
    public String confirmOperation(ConfirmOperationRequestDto requestDto) {
        String operationId = requestDto.getOperationId();
        log.info(format("Transaction with operationId: %s confirmed.", operationId));
        return operationId;
    }
}
