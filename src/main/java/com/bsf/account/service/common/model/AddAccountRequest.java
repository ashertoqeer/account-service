package com.bsf.account.service.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddAccountRequest {

    @NotBlank(message = "title must not be empty")
    @Length(max = 25, message = "title can't exceed 25 characters")
    private String title;

    @Email(message = "email is invalid")
    @NotBlank(message = "email must not be empty")
    @Length(max = 256, message = "max email length is 256 characters")
    private String email;

    @NotNull(message = "balance is required to create account")
    @Min(value = 0, message = "balance can't not be less than 0")
    @Digits(integer = 15, fraction = 2, message = "amount should be up to 15 digits and 2 decimal places")
    private BigDecimal balance;

}
