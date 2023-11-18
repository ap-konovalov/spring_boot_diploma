package ru.netology.moneytransferservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Amount {
    @Positive(message = "Amount value must be greater than 0")
    private int value;

    @NotBlank(message = "Amount currency must not be blank")
    private String currency;
}
