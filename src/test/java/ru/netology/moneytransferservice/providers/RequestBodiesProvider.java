package ru.netology.moneytransferservice.providers;

import lombok.experimental.UtilityClass;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.ConfirmOperationRequestDto;
import ru.netology.moneytransferservice.model.MoneyTransferRequestDto;

@UtilityClass
public class RequestBodiesProvider {

    public MoneyTransferRequestDto getMoneyTransferRequestBody() {
        return MoneyTransferRequestDto.builder()
                .cardFromNumber("7777666655554444")
                .cardFromValidTill("12/29")
                .cardFromCVV("233")
                .cardToNumber("1111222233334444")
                .amount(Amount.builder().value(100).currency("RUR").build())
                .build();
    }

    public ConfirmOperationRequestDto getConfirmOperationRequestBody() {
        return ConfirmOperationRequestDto.builder()
                .operationId("d5d25322-7ccf-11ee-b962-0242ac120002")
                .code("OK")
                .build();
    }
}
