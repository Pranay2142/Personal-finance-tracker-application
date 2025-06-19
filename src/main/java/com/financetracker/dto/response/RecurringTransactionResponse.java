package com.financetracker.dto.response;

import com.financetracker.entity.Category.TransactionType;
import com.financetracker.entity.RecurringTransaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RecurringTransactionResponse {
    private Long id;
    private Long userId;
    private Long accountId;
    private Long categoryId;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private RecurringTransaction.RecurrenceFrequency frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate nextExecutionDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
