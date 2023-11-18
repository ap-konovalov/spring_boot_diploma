package ru.netology.moneytransferservice.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MoneyTransferRequestDto {

    @NotBlank(message = "cardFromNumber must not be blank")
    @Pattern(regexp = "\\d{16}", message = "cardFromNumber must consists of 16 digits")
    private String cardFromNumber;

    @NotBlank(message = "cardFromValidTill must not be blank")
    @Pattern(regexp = "\\d{2}/\\d{2}", message = "cardFromValidTill should have format 12/22")
    private String cardFromValidTill;

    @NotBlank(message = "cardFromCVV must not be blank")
    @Pattern(regexp = "\\d{3}", message = "cardFromCVV must consists of 3 digits")
    private String cardFromCVV;

    @NotBlank(message = "cardToNumber must not be blank")
    @Pattern(regexp = "\\d{16}", message = "cardToNumber must consists of 16 digits")
    private String cardToNumber;

    @Valid
    private Amount amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyTransferRequestDto that = (MoneyTransferRequestDto) o;
        return Objects.equals(cardFromNumber, that.cardFromNumber) && Objects.equals(cardFromValidTill, that.cardFromValidTill) && Objects.equals(cardFromCVV, that.cardFromCVV) && Objects.equals(cardToNumber, that.cardToNumber) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardFromNumber, cardFromValidTill, cardFromCVV, cardToNumber, amount);
    }
}
