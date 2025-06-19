package com.financetracker.controller;

import com.financetracker.dto.response.AnalyticsResponse;
import com.financetracker.security.UserDetailsImpl;
import com.financetracker.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "Analytics & Financial Reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/summary")
    @Operation(summary = "Financial summary", description = "Get total income, expense, net")
    public ResponseEntity<AnalyticsResponse> summary(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(reportService.getSummary(currentUser.getUser()));
    }

    @GetMapping("/category-breakdown")
    @Operation(summary = "Category breakdown", description = "Spending by category")
    public ResponseEntity<AnalyticsResponse> byCategory(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(reportService.getCategoryBreakdown(currentUser.getUser()));
    }

    @GetMapping("/cash-flow")
    @Operation(summary = "Cash flow", description = "Daily net flow chart")
    public ResponseEntity<AnalyticsResponse> cashFlow(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(reportService.getCashFlow(currentUser.getUser()));
    }

    @GetMapping("/spending-trends")
    @Operation(summary = "Spending trends", description = "Monthly income vs expense trends")
    public ResponseEntity<AnalyticsResponse> trends(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(reportService.getSpendingTrends(currentUser.getUser()));
    }
}
