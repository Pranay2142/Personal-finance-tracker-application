package com.financetracker.service;

import com.financetracker.dto.request.RecurringTransactionRequest;
import com.financetracker.dto.response.RecurringTransactionResponse;
import com.financetracker.entity.*;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecurringTransactionService {

    private final RecurringTransactionRepository recurringTransactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<RecurringTransactionResponse> getActiveRecurringTransactionsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return recurringTransactionRepository.findByUserAndIsActiveTrue(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecurringTransactionResponse createRecurringTransaction(RecurringTransactionRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Account account = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        RecurringTransaction rt = new RecurringTransaction();
        rt.setUser(user);
        rt.setAccount(account);
        rt.setCategory(category);
        rt.setAmount(req.getAmount());
        rt.setType(req.getType());
        rt.setDescription(req.getDescription());
        rt.setFrequency(req.getFrequency());
        rt.setStartDate(req.getStartDate());
        rt.setEndDate(req.getEndDate());
        rt.setNextExecutionDate(req.getNextExecutionDate());
        rt.setIsActive(req.getIsActive() == null || req.getIsActive());

        recurringTransactionRepository.save(rt);

        return toResponse(rt);
    }

    @Transactional
    public RecurringTransactionResponse updateRecurringTransaction(Long id, RecurringTransactionRequest req) {
        RecurringTransaction rt = recurringTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurring transaction not found"));

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Account account = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        rt.setUser(user);
        rt.setAccount(account);
        rt.setCategory(category);
        rt.setAmount(req.getAmount());
        rt.setType(req.getType());
        rt.setDescription(req.getDescription());
        rt.setFrequency(req.getFrequency());
        rt.setStartDate(req.getStartDate());
        rt.setEndDate(req.getEndDate());
        rt.setNextExecutionDate(req.getNextExecutionDate());
        rt.setIsActive(req.getIsActive() == null || req.getIsActive());

        recurringTransactionRepository.save(rt);

        return toResponse(rt);
    }

    @Transactional
    public void deleteRecurringTransaction(Long id) {
        RecurringTransaction rt = recurringTransactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurring transaction not found"));
        recurringTransactionRepository.delete(rt);
    }

    private RecurringTransactionResponse toResponse(RecurringTransaction rt) {
        RecurringTransactionResponse resp = new RecurringTransactionResponse();
        resp.setId(rt.getId());
        resp.setUserId(rt.getUser().getId());
        resp.setAccountId(rt.getAccount().getId());
        resp.setCategoryId(rt.getCategory().getId());
        resp.setAmount(rt.getAmount());
        resp.setType(rt.getType());
        resp.setDescription(rt.getDescription());
        resp.setFrequency(rt.getFrequency());
        resp.setStartDate(rt.getStartDate());
        resp.setEndDate(rt.getEndDate());
        resp.setNextExecutionDate(rt.getNextExecutionDate());
        resp.setIsActive(rt.getIsActive());
        resp.setCreatedAt(rt.getCreatedAt());
        resp.setUpdatedAt(rt.getUpdatedAt());
        return resp;
    }
}
