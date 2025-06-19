package com.financetracker.dto.request;

import com.financetracker.entity.RecurringTransaction;
import com.financetracker.entity.Category.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RecurringTransactionRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long accountId;

    @NotNull
    private Long categoryId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private TransactionType type;

    private String description;

    @NotNull
    private RecurringTransaction.RecurrenceFrequency frequency;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private LocalDate nextExecutionDate;

    private Boolean isActive = true;
}
