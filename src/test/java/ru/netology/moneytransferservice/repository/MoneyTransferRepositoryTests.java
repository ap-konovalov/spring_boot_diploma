package ru.netology.moneytransferservice.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.netology.moneytransferservice.exceptions.NoSuchTransactionException;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;
import ru.netology.moneytransferservice.providers.RequestBodiesProvider;
import ru.netology.moneytransferservice.repositories.MonetTransferRepositoryImpl;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoneyTransferRepositoryTests {

    @Autowired
    private MonetTransferRepositoryImpl monetTransferRepository;

    @Test
    public void transferMoney() {
        UUID operationId = getOperationId();
        MoneyTransferRequestDto requestBody = RequestBodiesProvider.getMoneyTransferRequestBody();
        monetTransferRepository.transferMoney(requestBody, operationId);
        Assert.assertEquals(requestBody, monetTransferRepository.getMoneyTransferRequestFromDB(operationId));
    }

    @Test
    public void confirmOperation() throws NoSuchTransactionException {
        UUID operationId = getOperationId();
        MoneyTransferRequestDto requestBody = RequestBodiesProvider.getMoneyTransferRequestBody();
        monetTransferRepository.transferMoney(requestBody, operationId);
        monetTransferRepository.confirmOperation(operationId.toString());
        Assert.assertNull(monetTransferRepository.getMoneyTransferRequestFromDB(operationId));
    }

    private UUID getOperationId() {
        return UUID.randomUUID();
    }
}
