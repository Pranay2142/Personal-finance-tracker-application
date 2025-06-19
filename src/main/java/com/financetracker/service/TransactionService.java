package com.financetracker.service;

import com.financetracker.dto.request.TransactionRequest;
import com.financetracker.dto.response.TransactionResponse;
import com.financetracker.entity.*;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    @Transactional(readOnly = true)
    public Page<TransactionResponse> getAllTransactions(User user, Pageable pageable) {
        return transactionRepository.findByUser(user, pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(Long id, User user) {
        Transaction txn = transactionRepository.findById(id)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return toResponse(txn);
    }

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest req, User user) {
        Account acct = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Category cat = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        RecurringTransaction rec = null;
        if (req.getRecurringTransactionId() != null) {
            rec = recurringTransactionRepository.findById(req.getRecurringTransactionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Recurring transaction not found"));
        }

        Transaction txn = new Transaction();
        txn.setUser(user);
        txn.setAccount(acct);
        txn.setCategory(cat);
        txn.setAmount(req.getAmount());
        txn.setType(Category.TransactionType.valueOf(req.getType()));
        txn.setDescription(req.getDescription());
        txn.setTransactionDate(req.getTransactionDate());
        txn.setRecurringTransaction(rec);

        return toResponse(transactionRepository.save(txn));
    }

    @Transactional
    public TransactionResponse updateTransaction(Long id, TransactionRequest req, User user) {
        Transaction txn = transactionRepository.findById(id)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        Account acct = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Category cat = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        RecurringTransaction rec = null;
        if (req.getRecurringTransactionId() != null) {
            rec = recurringTransactionRepository.findById(req.getRecurringTransactionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Recurring transaction not found"));
        }

        txn.setAccount(acct);
        txn.setCategory(cat);
        txn.setAmount(req.getAmount());
        txn.setType(Category.TransactionType.valueOf(req.getType()));
        txn.setDescription(req.getDescription());
        txn.setTransactionDate(req.getTransactionDate());
        txn.setRecurringTransaction(rec);

        return toResponse(transactionRepository.save(txn));
    }

    @Transactional
    public void deleteTransaction(Long id, User user) {
        Transaction txn = transactionRepository.findById(id)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        transactionRepository.delete(txn);
    }

    private TransactionResponse toResponse(Transaction t) {
        TransactionResponse dto = new TransactionResponse();
        dto.setId(t.getId());
        dto.setAccountName(t.getAccount().getName());
        dto.setCategoryName(t.getCategory().getName());
        dto.setAmount(t.getAmount());
        dto.setType(t.getType().name());
        dto.setDescription(t.getDescription());
        dto.setTransactionDate(t.getTransactionDate());
        dto.setCreatedAt(t.getCreatedAt());
        return dto;
    }
}
