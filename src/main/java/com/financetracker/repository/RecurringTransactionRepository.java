package com.financetracker.repository;

import com.financetracker.entity.RecurringTransaction;
import com.financetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
    List<RecurringTransaction> findByUserAndIsActiveTrue(User user);
    List<RecurringTransaction> findByNextExecutionDateBeforeAndIsActiveTrue(LocalDate date);
}
