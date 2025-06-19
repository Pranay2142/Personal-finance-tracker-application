package com.financetracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.financetracker.entity.Category.TransactionType;

@Data
public class CategoryRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private TransactionType type;

    @Size(max = 7)
    private String color;

    @Size(max = 50)
    private String icon;
}
