package com.financetracker.dto.request;

import com.financetracker.entity.Budget.BudgetPeriod;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BudgetRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private Long categoryId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private BudgetPeriod period;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;
}
