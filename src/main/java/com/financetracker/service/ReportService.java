package com.financetracker.service;

import com.financetracker.dto.response.AnalyticsResponse;
import com.financetracker.entity.Transaction;
import com.financetracker.entity.User;
import com.financetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public AnalyticsResponse getSummary(User user) {
        List<Transaction> txns = transactionRepository.findByUser(user, null).getContent();

        BigDecimal income = txns.stream()
                .filter(t -> t.getType().name().equals("INCOME"))
                .map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expense = txns.stream()
                .filter(t -> t.getType().name().equals("EXPENSE"))
                .map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        AnalyticsResponse resp = new AnalyticsResponse();
        resp.setTotalIncome(income);
        resp.setTotalExpense(expense);
        resp.setNetIncome(income.subtract(expense));
        return resp;
    }

    @Transactional(readOnly = true)
    public AnalyticsResponse getCategoryBreakdown(User user) {
        List<Transaction> txns = transactionRepository.findByUser(user, null).getContent();

        Map<String, BigDecimal> breakdown = txns.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.mapping(Transaction::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        AnalyticsResponse resp = new AnalyticsResponse();
        resp.setCategoryBreakdown(breakdown);
        return resp;
    }

    @Transactional(readOnly = true)
    public AnalyticsResponse getCashFlow(User user) {
        List<Transaction> txns = transactionRepository.findByUser(user, null).getContent();

        Map<LocalDate, BigDecimal> flow = txns.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getTransactionDate,
                        Collectors.mapping(
                                t -> t.getType().name().equals("INCOME") ? t.getAmount() : t.getAmount().negate(),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        AnalyticsResponse resp = new AnalyticsResponse();
        resp.setCashFlow(flow);
        return resp;
    }

    @Transactional(readOnly = true)
    public AnalyticsResponse getSpendingTrends(User user) {
        List<Transaction> txns = transactionRepository.findByUser(user, null).getContent();

        Map<String, BigDecimal> monthly = txns.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getTransactionDate().getYear() + "-" + String.format("%02d", t.getTransactionDate().getMonthValue()),
                        Collectors.mapping(
                                t -> t.getType().name().equals("INCOME") ? t.getAmount() : t.getAmount().negate(),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        AnalyticsResponse resp = new AnalyticsResponse();
        resp.setTrendsMonthly(monthly);
        return resp;
    }
}
