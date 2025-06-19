package com.financetracker.controller;

import com.financetracker.dto.request.AccountRequest;
import com.financetracker.dto.response.AccountResponse;
import com.financetracker.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/user/{userId}")
    public AccountResponse createAccount(@PathVariable Long userId,
                                         @Valid @RequestBody AccountRequest request) {
        return accountService.createAccount(userId, request);
    }

    @GetMapping("/user/{userId}")
    public List<AccountResponse> getAllAccountsByUser(@PathVariable Long userId) {
        return accountService.getAllAccountsByUser(userId);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PutMapping("/{id}")
    public AccountResponse updateAccount(@PathVariable Long id,
                                         @Valid @RequestBody AccountRequest request) {
        return accountService.updateAccount(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
