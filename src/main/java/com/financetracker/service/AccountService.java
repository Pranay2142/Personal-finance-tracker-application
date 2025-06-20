package com.financetracker.service;

import com.financetracker.dto.request.AccountRequest;
import com.financetracker.dto.response.AccountResponse;
import com.financetracker.entity.Account;
import com.financetracker.entity.User;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.repository.AccountRepository;
import com.financetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    // Programmatic cache eviction method
    @Autowired
    private org.springframework.cache.CacheManager cacheManager;

    @Cacheable(value = "accountsByUser", key = "#userId")
    public List<AccountResponse> getAllAccountsByUser(Long userId) {
        System.err.println("🔄 Fetching from DB for userId = " + userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return accountRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "accountById", key = "#accountId")
    public AccountResponse getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return mapToResponse(account);
    }

    @CacheEvict(value = "accountsByUser", key = "#userId")
    public AccountResponse createAccount(Long userId, AccountRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Account account = new Account();
        account.setUser(user);
        account.setName(request.getName());
        account.setType(request.getType());
        account.setBalance(request.getBalance());
        account.setCurrency(request.getCurrency());
        account.setIsActive(request.getIsActive());

        Account saved = accountRepository.save(account);
        return mapToResponse(saved);
    }

    // Here we remove CacheEvict annotation and evict programmatically
    public AccountResponse updateAccount(Long accountId, AccountRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        Long userId = account.getUser().getId();

        account.setName(request.getName());
        account.setType(request.getType());
        account.setBalance(request.getBalance());
        account.setCurrency(request.getCurrency());
        account.setIsActive(request.getIsActive());

        Account updated = accountRepository.save(account);

        // Programmatic cache eviction
        evictCaches(accountId, userId);

        return mapToResponse(updated);
    }

    private void evictCaches(Long accountId, Long userId) {
        Objects.requireNonNull(cacheManager.getCache("accountById")).evict(accountId);
        Objects.requireNonNull(cacheManager.getCache("accountsByUser")).evict(userId);
    }

    // Same here: remove CacheEvict annotations and evict programmatically
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        Long userId = account.getUser().getId();

        accountRepository.delete(account);

        // Evict caches after delete
        evictCaches(accountId, userId);
    }

    private AccountResponse mapToResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setName(account.getName());
        response.setType(account.getType());
        response.setBalance(account.getBalance());
        response.setCurrency(account.getCurrency());
        response.setIsActive(account.getIsActive());
        response.setCreatedAt(account.getCreatedAt());
        response.setUpdatedAt(account.getUpdatedAt());
        return response;
    }
}


