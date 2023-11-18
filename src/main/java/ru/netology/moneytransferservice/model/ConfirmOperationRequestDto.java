package ru.netology.moneytransferservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ConfirmOperationRequestDto {
    @NotBlank(message = "operationId must not be blank")
    private String operationId;

    @NotBlank(message = "code must not be blank")
    private String code;
}
