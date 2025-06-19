package com.financetracker.dto.request;

import com.financetracker.entity.Account.AccountType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    @NotBlank
    private String name;

    @NotNull
    private AccountType type;

    @DecimalMin("0.00")
    private BigDecimal balance = BigDecimal.ZERO;

    @Size(max = 3)
    private String currency = "USD";

    private Boolean isActive = true;

    // Getters and Setters
}
