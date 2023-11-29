package ru.netology.moneytransferservice.controller.moneytransferservice;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.moneytransferservice.controllers.MoneyTransferController;
import ru.netology.moneytransferservice.providers.RequestBodiesProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionHandlerAdviceTests {

    private static final String CONFIRM_OPERATION_PATH = "/confirmOperation";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoneyTransferController moneyTransferController;

    @Test
    public void test500ErrorHandling() throws Exception {
        when(moneyTransferController.confirmOperation(any()))
                .thenThrow(new RuntimeException());

        String expectedErrorMessage = "{\"message\":\"Internal Server Error\",\"id\":2}";

        mockMvc.perform(post(CONFIRM_OPERATION_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(RequestBodiesProvider.getConfirmOperationRequestBody())))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertEquals(expectedErrorMessage, result.getResponse().getContentAsString()));
    }
}
