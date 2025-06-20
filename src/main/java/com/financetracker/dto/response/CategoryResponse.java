package com.financetracker.dto.response;

import com.financetracker.entity.Category.TransactionType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CategoryResponse implements Serializable {
    private Long id;
    private String name;
    private TransactionType type;
    private String color;
    private String icon;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
