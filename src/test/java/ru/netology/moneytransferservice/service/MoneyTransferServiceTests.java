package ru.netology.moneytransferservice.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.netology.moneytransferservice.exceptions.NoSuchTransactionException;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;
import ru.netology.moneytransferservice.providers.RequestBodiesProvider;
import ru.netology.moneytransferservice.repositories.MonetTransferRepositoryImpl;
import ru.netology.moneytransferservice.services.MoneyTransferService;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoneyTransferServiceTests {

    @MockBean
    private MonetTransferRepositoryImpl monetTransferRepository;

    @Autowired
    private MoneyTransferService moneyTransferService;

    @Test
    public void transferMoney() {
        MoneyTransferRequestDto requestBody = RequestBodiesProvider.getMoneyTransferRequestBody();
        String operationId = moneyTransferService.transferMoney(requestBody);
        verify(monetTransferRepository).transferMoney(requestBody, UUID.fromString(operationId));
    }

    @Test
    public void confirmOperation() throws NoSuchTransactionException {
        ConfirmOperationRequestDto requestBody = RequestBodiesProvider.getConfirmOperationRequestBody();
        moneyTransferService.confirmOperation(requestBody);
        verify(monetTransferRepository).confirmOperation(requestBody.getOperationId());
    }
}
