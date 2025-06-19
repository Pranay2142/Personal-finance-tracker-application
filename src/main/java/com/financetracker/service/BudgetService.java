package com.financetracker.service;

import com.financetracker.dto.request.BudgetRequest;
import com.financetracker.dto.response.BudgetSummaryResponse;
import com.financetracker.entity.*;
import com.financetracker.exception.*;
import com.financetracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public List<BudgetSummaryResponse> listBudgets(User user) {
        return budgetRepository.findByUserAndIsActiveTrue(user).stream()
                .map(b -> mapToSummary(b, user))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BudgetSummaryResponse getBudgetProgress(Long id, User user) {
        Budget budget = findActiveBudgetById(id, user);
        return mapToSummary(budget, user);
    }

    @Transactional
    public BudgetSummaryResponse createBudget(BudgetRequest req, User user) {
        Category cat = categoryRepository.findById(req.getCategoryId())
                .filter(c -> c.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setCategory(cat);
        budget.setName(req.getName());
        budget.setAmount(req.getAmount());
        budget.setPeriod(req.getPeriod());
        budget.setStartDate(req.getStartDate());
        budget.setEndDate(req.getEndDate());
        budget.setIsActive(true);

        return mapToSummary(budgetRepository.save(budget), user);
    }

    @Transactional
    public BudgetSummaryResponse updateBudget(Long id, BudgetRequest req, User user) {
        Budget budget = findActiveBudgetById(id, user);

        Category cat = categoryRepository.findById(req.getCategoryId())
                .filter(c -> c.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        budget.setCategory(cat);
        budget.setName(req.getName());
        budget.setAmount(req.getAmount());
        budget.setPeriod(req.getPeriod());
        budget.setStartDate(req.getStartDate());
        budget.setEndDate(req.getEndDate());

        return mapToSummary(budgetRepository.save(budget), user);
    }

    @Transactional
    public void deleteBudget(Long id, User user) {
        Budget budget = findActiveBudgetById(id, user);
        budget.setIsActive(false);
        budgetRepository.save(budget);
    }

    private BudgetSummaryResponse mapToSummary(Budget b, User user) {
        BigDecimal spent = transactionRepository.findByUser(user, null).stream()
                .filter(t -> t.getCategory().equals(b.getCategory()) &&
                        !t.getTransactionDate().isBefore(b.getStartDate()) &&
                        (b.getEndDate() == null || !t.getTransactionDate().isAfter(b.getEndDate())))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BudgetSummaryResponse resp = new BudgetSummaryResponse();
        resp.setId(b.getId());
        resp.setName(b.getName());
        resp.setPeriod(b.getPeriod());
        resp.setAmount(b.getAmount());
        resp.setSpent(spent);
        resp.setRemaining(b.getAmount().subtract(spent));
        resp.setStartDate(b.getStartDate());
        resp.setEndDate(b.getEndDate());
        resp.setIsActive(b.getIsActive());
        return resp;
    }

    private Budget findActiveBudgetById(Long id, User user) {
        return budgetRepository.findById(id)
                .filter(b -> b.getUser().getId().equals(user.getId()) && b.getIsActive())
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));
    }
}
