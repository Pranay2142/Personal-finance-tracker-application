package com.financetracker.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionResponse implements Serializable {
    private Long id;
    private String accountName;
    private String categoryName;
    private BigDecimal amount;
    private String type;
    private String description;
    private LocalDate transactionDate;
    private LocalDateTime createdAt;
}
