package com.financetracker.repository;

import com.financetracker.entity.Category;
import com.financetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    boolean existsByUserAndNameAndType(User user, String name, Category.TransactionType type);
}
