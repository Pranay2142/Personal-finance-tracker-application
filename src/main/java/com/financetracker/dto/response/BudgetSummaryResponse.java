package com.financetracker.dto.response;

import com.financetracker.entity.Budget.BudgetPeriod;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BudgetSummaryResponse {
    private Long id;
    private String name;
    private BudgetPeriod period;
    private BigDecimal amount;
    private BigDecimal spent;
    private BigDecimal remaining;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}
