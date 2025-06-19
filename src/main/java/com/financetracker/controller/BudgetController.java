package com.financetracker.controller;

import com.financetracker.dto.request.BudgetRequest;
import com.financetracker.dto.response.BudgetSummaryResponse;
import com.financetracker.security.UserDetailsImpl;
import com.financetracker.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@Tag(name = "Budgets", description = "Budget management APIs")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping
    @Operation(summary = "List budgets", description = "List all active budgets")
    public ResponseEntity<List<BudgetSummaryResponse>> listBudgets(
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(budgetService.listBudgets(currentUser.getUser()));
    }

    @GetMapping("/{id}/progress")
    @Operation(summary = "Budget progress", description = "Get spending progress for a budget")
    public ResponseEntity<BudgetSummaryResponse> getProgress(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(budgetService.getBudgetProgress(id, currentUser.getUser()));
    }

    @PostMapping
    @Operation(summary = "Create budget", description = "Create new budget")
    public ResponseEntity<BudgetSummaryResponse> createBudget(
            @Valid @RequestBody BudgetRequest req,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(budgetService.createBudget(req, currentUser.getUser()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update budget", description = "Update budget details")
    public ResponseEntity<BudgetSummaryResponse> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetRequest req,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(budgetService.updateBudget(id, req, currentUser.getUser()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete budget", description = "Deactivate budget")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        budgetService.deleteBudget(id, currentUser.getUser());
        return ResponseEntity.noContent().build();
    }
}
