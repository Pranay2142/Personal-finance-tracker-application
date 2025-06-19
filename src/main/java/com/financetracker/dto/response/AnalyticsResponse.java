package com.financetracker.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
public class AnalyticsResponse {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netIncome;
    private Map<String, BigDecimal> categoryBreakdown;
    private Map<LocalDate, BigDecimal> cashFlow;        // date → net amount
    private Map<String, BigDecimal> trendsMonthly;      // "YYYY-MM" → net amount
}
