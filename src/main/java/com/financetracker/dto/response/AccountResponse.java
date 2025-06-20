package com.financetracker.dto.response;

import com.financetracker.entity.Account.AccountType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountResponse implements Serializable {
    private Long id;
    private String name;
    private AccountType type;
    private BigDecimal balance;
    private String currency;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
}
