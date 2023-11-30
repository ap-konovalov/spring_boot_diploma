package ru.netology.moneytransferservice.repositories;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.exceptions.NoSuchTransactionException;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

@Repository
@NoArgsConstructor
@Slf4j
public class MonetTransferRepositoryImpl implements MonetTransferRepository {

    private Map<UUID, MoneyTransferRequestDto> transactions = new ConcurrentHashMap<>();

    @Override
    public void transferMoney(MoneyTransferRequestDto requestDto, UUID operationId) {
        transactions.put(operationId, requestDto);
        log.info(format("Transfer money operation from card: %s to card: %s created. Amount: %s. OperationId: %s",
                requestDto.getCardFromNumber(), requestDto.getCardToNumber(), requestDto.getAmount().getValue(),
                operationId.toString()));
    }

    @Override
    public void confirmOperation(String operationId) throws NoSuchTransactionException {
        UUID operationUUID = UUID.fromString(operationId);
        if (transactions.containsKey(operationUUID)) {
            transactions.remove(operationUUID);
            log.info(format("Transaction with operationId: %s confirmed.", operationId));
        } else {
            throw new NoSuchTransactionException("Transaction not found");
        }
    }

    public MoneyTransferRequestDto getMoneyTransferRequestFromDB(UUID operationId) {
        return transactions.get(operationId);
    }
}
