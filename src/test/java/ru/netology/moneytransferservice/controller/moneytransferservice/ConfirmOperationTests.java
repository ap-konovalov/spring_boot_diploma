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
import ru.netology.moneytransferservice.model.OperationResponseDto;
import ru.netology.moneytransferservice.providers.RequestBodiesProvider;

import java.util.UUID;

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
        ResponseEntity<OperationResponseDto> response = testRestTemplate.postForEntity(HOST +
                        moneyTransferApp.getMappedPort(5500) + CONFIRM_OPERATION_PATH,
                RequestBodiesProvider.getConfirmOperationRequestBody(), OperationResponseDto.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        try {
            UUID.fromString(response.getBody().getOperationId());
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Response field operationId not UUID");
        }
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
}
