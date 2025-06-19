package com.financetracker.controller;

import com.financetracker.dto.request.GoalRequest;
import com.financetracker.dto.response.GoalResponse;
import com.financetracker.security.UserDetailsImpl;
import com.financetracker.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/goals")
@Tag(name = "Goals", description = "Financial goal management APIs")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping
    @Operation(summary = "List goals", description = "Retrieve all financial goals")
    public ResponseEntity<List<GoalResponse>> listGoals(
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(goalService.listGoals(currentUser.getUser()));
    }

    @PostMapping
    @Operation(summary = "Create goal", description = "Create a new financial goal")
    public ResponseEntity<GoalResponse> createGoal(
            @Valid @RequestBody GoalRequest req,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(goalService.createGoal(req, currentUser.getUser()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update goal", description = "Update financial goal")
    public ResponseEntity<GoalResponse> updateGoal(
            @PathVariable Long id,
            @Valid @RequestBody GoalRequest req,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(goalService.updateGoal(id, req, currentUser.getUser()));
    }



    @PostMapping("/{id}/contribute")
    @Operation(summary = "Add contribution", description = "Add amount toward goal progress")
    public ResponseEntity<GoalResponse> contribute(
            @PathVariable Long id,
            @Valid @RequestBody ContributionRequest req,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(goalService.contribute(id, req.getAmount(), currentUser.getUser()));
    }

    @Data
    public static class ContributionRequest {
        @NotNull
        @DecimalMin("0.01")
        private BigDecimal amount;
    }
}
