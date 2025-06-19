package com.financetracker.service;

import com.financetracker.dto.request.GoalRequest;
import com.financetracker.dto.response.GoalResponse;
import com.financetracker.entity.FinancialGoal;
import com.financetracker.entity.User;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.repository.FinancialGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final FinancialGoalRepository goalRepo;

    @Transactional(readOnly = true)
    public List<GoalResponse> listGoals(User user) {
        return goalRepo.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public GoalResponse createGoal(GoalRequest req, User user) {
        FinancialGoal g = new FinancialGoal();
        g.setUser(user);
        g.setName(req.getName());
        g.setDescription(req.getDescription());
        g.setTargetAmount(req.getTargetAmount());
        g.setTargetDate(req.getTargetDate());
        if (req.getPriority() != null) g.setPriority(req.getPriority());
        if (req.getStatus() != null) g.setStatus(req.getStatus());
        goalRepo.save(g);
        return toResponse(g);
    }

    @Transactional
    public GoalResponse updateGoal(Long id, GoalRequest req, User user) {
        FinancialGoal g = getById(id, user);
        g.setName(req.getName());
        g.setDescription(req.getDescription());
        g.setTargetAmount(req.getTargetAmount());
        g.setTargetDate(req.getTargetDate());
        if (req.getPriority() != null) g.setPriority(req.getPriority());
        if (req.getStatus() != null) g.setStatus(req.getStatus());
        return toResponse(goalRepo.save(g));
    }

    @Transactional
    public GoalResponse contribute(Long id, BigDecimal amount, User user) {
        FinancialGoal g = getById(id, user);
        g.setCurrentAmount(g.getCurrentAmount().add(amount));
        if (g.getCurrentAmount().compareTo(g.getTargetAmount()) >= 0) {
            g.setStatus(FinancialGoal.GoalStatus.COMPLETED);
        }
        return toResponse(goalRepo.save(g));
    }

    private FinancialGoal getById(Long id, User user) {
        return goalRepo.findById(id)
                .filter(g -> g.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Financial goal not found"));
    }

    private GoalResponse toResponse(FinancialGoal g) {
        GoalResponse r = new GoalResponse();
        r.setId(g.getId());
        r.setName(g.getName());
        r.setDescription(g.getDescription());
        r.setTargetAmount(g.getTargetAmount());
        r.setCurrentAmount(g.getCurrentAmount());
        r.setTargetDate(g.getTargetDate());
        r.setPriority(g.getPriority());
        r.setStatus(g.getStatus());
        r.setCreatedAt(g.getCreatedAt());
        r.setUpdatedAt(g.getUpdatedAt());
        return r;
    }
}
