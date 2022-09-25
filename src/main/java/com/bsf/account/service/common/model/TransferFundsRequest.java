package com.bsf.account.service.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferFundsRequest {

    @NotBlank(message = "reason must not be empty")
    @Length(max = 25, message = "reason can't exceed 25 characters")
    private String reason;

    @NotNull(message = "amount is required")
    @Min(value = 1, message = "amount can't not be less than 1")
    @Digits(integer = 15, fraction = 2, message = "amount should be up to 15 digits and 2 decimal places")
    private BigDecimal amount;

    @NotNull(message = "transferToAccount is required")
    @Min(value = 1, message = "invalid value for transferToAccount")
    private Long receiverAccount;

}
