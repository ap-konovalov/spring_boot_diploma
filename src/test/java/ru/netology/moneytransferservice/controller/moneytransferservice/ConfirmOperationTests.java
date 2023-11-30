package ru.netology.moneytransferservice.controller.moneytransferservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.ErrorResponseDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;
import ru.netology.moneytransferservice.model.OperationResponseDto;
import ru.netology.moneytransferservice.providers.RequestBodiesProvider;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfirmOperationTests {

    private static final String HOST = "http://localhost:";
    private static final String CONFIRM_OPERATION_PATH = "/confirmOperation";
    @Autowired
    TestRestTemplate testRestTemplate;

    @Container
    private final GenericContainer<?> moneyTransferApp = new GenericContainer<>("money_transfer_back:latest")
            .withExposedPorts(5500);

    @Test
    void confirmOperationShouldReturnOperationId() {
        MoneyTransferRequestDto transferRequestBody = RequestBodiesProvider.getMoneyTransferRequestBody();
        ResponseEntity<OperationResponseDto> transferResponse = testRestTemplate.postForEntity(HOST +
                        moneyTransferApp.getMappedPort(5500) + "/transfer",
                transferRequestBody, OperationResponseDto.class);
        ConfirmOperationRequestDto confirmOperationRequestBody = RequestBodiesProvider.getConfirmOperationRequestBody();
        String operationId = transferResponse.getBody().getOperationId();
        confirmOperationRequestBody.setOperationId(operationId);

        ResponseEntity<OperationResponseDto> response = testRestTemplate.postForEntity(HOST +
                        moneyTransferApp.getMappedPort(5500) + CONFIRM_OPERATION_PATH,
                confirmOperationRequestBody, OperationResponseDto.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(operationId, response.getBody().getOperationId());
    }

    @Test
    void errorDueToOperationIdValidation() {
        ConfirmOperationRequestDto requestBody = RequestBodiesProvider.getConfirmOperationRequestBody();
        requestBody.setOperationId("");
        ResponseEntity<ErrorResponseDto> response = testRestTemplate.postForEntity(HOST +
                moneyTransferApp.getMappedPort(5500) + CONFIRM_OPERATION_PATH, requestBody, ErrorResponseDto.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertAll(
                () -> assertEquals(0, response.getBody().getId()),
                () -> assertEquals("[operationId must not be blank]", response.getBody().getMessage())
        );
    }

    @Test
    void errorDueToCodeValidation() {
        ConfirmOperationRequestDto requestBody = RequestBodiesProvider.getConfirmOperationRequestBody();
        requestBody.setCode("");
        ResponseEntity<ErrorResponseDto> response = testRestTemplate.postForEntity(HOST +
                moneyTransferApp.getMappedPort(5500) + CONFIRM_OPERATION_PATH, requestBody, ErrorResponseDto.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertAll(
                () -> assertEquals(0, response.getBody().getId()),
                () -> assertEquals("[code must not be blank]", response.getBody().getMessage())
        );
    }

    @Test
    void errorDueToTransactionNotFound() {
        ResponseEntity<ErrorResponseDto> response = testRestTemplate.postForEntity(HOST +
                        moneyTransferApp.getMappedPort(5500) + CONFIRM_OPERATION_PATH,
                RequestBodiesProvider.getConfirmOperationRequestBody(),
                ErrorResponseDto.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertAll(
                () -> assertEquals(3, response.getBody().getId()),
                () -> assertEquals("Transaction not found", response.getBody().getMessage())
        );
    }
}
