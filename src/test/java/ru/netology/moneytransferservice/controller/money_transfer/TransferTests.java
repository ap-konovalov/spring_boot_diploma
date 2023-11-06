package ru.netology.moneytransferservice.controller.money_transfer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.moneytransferservice.entitites.ErrorResponseDto;
import ru.netology.moneytransferservice.entitites.MoneyTransferRequestDto;
import ru.netology.moneytransferservice.entitites.OperationResponseDto;
import ru.netology.moneytransferservice.providers.RequestBodiesProvider;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferTests {

    private static final String HOST = "http://localhost:";
    private static final String TRANSFER_PATH = "/transfer";
    @Autowired
    TestRestTemplate testRestTemplate;

    @Container
    private final GenericContainer<?> moneyTransferApp = new GenericContainer<>("money_transfer_back:latest")
            .withExposedPorts(5500);

    @Test
    void transferShouldReturnOperationId() {
        ResponseEntity<OperationResponseDto> response = testRestTemplate.postForEntity(HOST +
                        moneyTransferApp.getMappedPort(5500) + TRANSFER_PATH,
                RequestBodiesProvider.getMoneyTransferRequestBody(), OperationResponseDto.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        try {
            UUID.fromString(response.getBody().getOperationId());
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Response field operationId not UUID");
        }
    }

    @Test
    void errorDueToCardFromValidation() {
        MoneyTransferRequestDto requestBody = RequestBodiesProvider.getMoneyTransferRequestBody();
        requestBody.setCardFromNumber("1234");
        ResponseEntity<ErrorResponseDto> response = testRestTemplate.postForEntity(HOST +
                moneyTransferApp.getMappedPort(5500) + TRANSFER_PATH, requestBody, ErrorResponseDto.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertAll(
                () -> assertEquals(0, response.getBody().getId()),
                () -> assertEquals("[cardFromNumber must consists of 16 digits]", response.getBody().getMessage())
        );
    }

    @Test
    void errorDueToCardFromValidTillValidation() {
        MoneyTransferRequestDto requestBody = RequestBodiesProvider.getMoneyTransferRequestBody();
        requestBody.setCardFromValidTill("12/4");
        ResponseEntity<ErrorResponseDto> response = testRestTemplate.postForEntity(HOST +
                moneyTransferApp.getMappedPort(5500) + TRANSFER_PATH, requestBody, ErrorResponseDto.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertAll(
                () -> assertEquals(0, response.getBody().getId()),
                () -> assertEquals("[cardFromValidTill should have format 12/22]", response.getBody().getMessage())
        );
    }

    @Test
    void errorDueToCardFromCVValidation() {
        MoneyTransferRequestDto requestBody = RequestBodiesProvider.getMoneyTransferRequestBody();
        requestBody.setCardFromCVV("12");
        ResponseEntity<ErrorResponseDto> response = testRestTemplate.postForEntity(HOST +
                moneyTransferApp.getMappedPort(5500) + TRANSFER_PATH, requestBody, ErrorResponseDto.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertAll(
                () -> assertEquals(0, response.getBody().getId()),
                () -> assertEquals("[cardFromCVV must consists of 3 digits]", response.getBody().getMessage())
        );
    }

    @Test
    void errorDueToCardToNumberValidation() {
        MoneyTransferRequestDto requestBody = RequestBodiesProvider.getMoneyTransferRequestBody();
        requestBody.setCardToNumber("1256");
        ResponseEntity<ErrorResponseDto> response = testRestTemplate.postForEntity(HOST +
                moneyTransferApp.getMappedPort(5500) + TRANSFER_PATH, requestBody, ErrorResponseDto.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertAll(
                () -> assertEquals(0, response.getBody().getId()),
                () -> assertEquals("[cardToNumber must consists of 16 digits]", response.getBody().getMessage())
        );
    }

    @Test
    void errorDueToAmountValueValidation() {
        MoneyTransferRequestDto requestBody = RequestBodiesProvider.getMoneyTransferRequestBody();
        requestBody.getAmount().setValue(0);
        ResponseEntity<ErrorResponseDto> response = testRestTemplate.postForEntity(HOST +
                moneyTransferApp.getMappedPort(5500) + TRANSFER_PATH, requestBody, ErrorResponseDto.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertAll(
                () -> assertEquals(0, response.getBody().getId()),
                () -> assertEquals("[Amount value must be greater than 0]", response.getBody().getMessage())
        );
    }

    @Test
    void errorDueToAmountCurrencyValidation() {
        MoneyTransferRequestDto requestBody = RequestBodiesProvider.getMoneyTransferRequestBody();
        requestBody.getAmount().setCurrency("");
        ResponseEntity<ErrorResponseDto> response = testRestTemplate.postForEntity(HOST +
                moneyTransferApp.getMappedPort(5500) + TRANSFER_PATH, requestBody, ErrorResponseDto.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertAll(
                () -> assertEquals(0, response.getBody().getId()),
                () -> assertEquals("[Amount currency must not be blank]", response.getBody().getMessage())
        );
    }
}
