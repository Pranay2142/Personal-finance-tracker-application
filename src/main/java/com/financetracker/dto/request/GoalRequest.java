package com.financetracker.dto.request;

import com.financetracker.entity.FinancialGoal.GoalPriority;
import com.financetracker.entity.FinancialGoal.GoalStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class GoalRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    private String description;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal targetAmount;

    private LocalDate targetDate;

    private GoalPriority priority;

    private GoalStatus status;
}
