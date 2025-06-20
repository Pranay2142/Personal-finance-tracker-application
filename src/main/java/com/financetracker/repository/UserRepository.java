package com.financetracker.repository;

import com.financetracker.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Cacheable(value = "existsByUsername", key = "#username")
    Boolean existsByUsername(String username);

    @Cacheable(value = "existsByEmail", key = "#email")
    Boolean existsByEmail(String email);

}