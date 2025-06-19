package com.financetracker.controller;

import com.financetracker.dto.request.TransactionRequest;
import com.financetracker.dto.response.TransactionResponse;
import com.financetracker.entity.User;
import com.financetracker.security.UserDetailsImpl;
import com.financetracker.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Transaction management APIs")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    @Operation(summary = "List transactions", description = "Retrieve paginated list of transactions")
    public ResponseEntity<Page<TransactionResponse>> getAllTransactions(
            @AuthenticationPrincipal UserDetailsImpl currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("transactionDate").descending());
        Page<TransactionResponse> result = transactionService.getAllTransactions(currentUser.getUser(), pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction", description = "Retrieve a single transaction by ID")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        TransactionResponse response = transactionService.getTransactionById(id, currentUser.getUser());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create transaction", description = "Create a new transaction")
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        TransactionResponse response = transactionService.createTransaction(request, currentUser.getUser());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update transaction", description = "Update an existing transaction")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        TransactionResponse response = transactionService.updateTransaction(id, request, currentUser.getUser());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete transaction", description = "Delete a transaction by ID")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        transactionService.deleteTransaction(id, currentUser.getUser());
        return ResponseEntity.noContent().build();
    }
}
