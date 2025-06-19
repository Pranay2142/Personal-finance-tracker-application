package com.financetracker.controller;

import com.financetracker.dto.request.RecurringTransactionRequest;
import com.financetracker.dto.response.RecurringTransactionResponse;
import com.financetracker.service.RecurringTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurring-transactions")
@Tag(name = "Recurring Transactions", description = "Manage recurring transactions")
@RequiredArgsConstructor
public class RecurringTransactionController {

    private final RecurringTransactionService recurringTransactionService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get active recurring transactions for user")
    public ResponseEntity<List<RecurringTransactionResponse>> getActiveByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recurringTransactionService.getActiveRecurringTransactionsByUser(userId));
    }

    @PostMapping
    @Operation(summary = "Create a recurring transaction")
    public ResponseEntity<RecurringTransactionResponse> create(@Valid @RequestBody RecurringTransactionRequest request) {
        return ResponseEntity.ok(recurringTransactionService.createRecurringTransaction(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a recurring transaction")
    public ResponseEntity<RecurringTransactionResponse> update(@PathVariable Long id,
                                                               @Valid @RequestBody RecurringTransactionRequest request) {
        return ResponseEntity.ok(recurringTransactionService.updateRecurringTransaction(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a recurring transaction")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recurringTransactionService.deleteRecurringTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
