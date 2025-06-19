package com.financetracker.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {

    @NotNull
    private Long accountId;

    @NotNull
    private Long categoryId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private String type; // INCOME or EXPENSE

    private String description;

    @NotNull
    private LocalDate transactionDate;

    private Long recurringTransactionId;
}
