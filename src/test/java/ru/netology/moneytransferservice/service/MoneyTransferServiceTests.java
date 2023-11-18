package ru.netology.moneytransferservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.providers.RequestBodiesProvider;
import ru.netology.moneytransferservice.repositories.MonetTransferRepositoryImpl;
import ru.netology.moneytransferservice.services.MoneyTransferService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoneyTransferServiceTests {

    @MockBean
    private MonetTransferRepositoryImpl monetTransferRepository;

    @Autowired
    private MoneyTransferService moneyTransferService;

    @Test
    public void transferMoney() {
        String expectedOperationId = UUID.randomUUID().toString();
        given(this.monetTransferRepository.transferMoney(any()))
                .willReturn(expectedOperationId);
        Assertions.assertEquals(expectedOperationId, moneyTransferService.transferMoney(any()));
    }

    @Test
    public void confirmOperation() {
        ConfirmOperationRequestDto requestBody = RequestBodiesProvider.getConfirmOperationRequestBody();
        given(this.monetTransferRepository.confirmOperation(any()))
                .willReturn(requestBody.getOperationId());
        Assertions.assertEquals(requestBody.getOperationId(), moneyTransferService.confirmOperation(requestBody));
    }
}
