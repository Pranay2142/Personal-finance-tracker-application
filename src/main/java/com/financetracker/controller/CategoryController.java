package com.financetracker.controller;

import com.financetracker.dto.request.CategoryRequest;
import com.financetracker.dto.response.CategoryResponse;
import com.financetracker.security.UserDetailsImpl;
import com.financetracker.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Category management APIs")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "List categories", description = "Get all categories for current user")
    public ResponseEntity<List<CategoryResponse>> getAll(
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        List<CategoryResponse> list = categoryService.listCategories(currentUser.getUser());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @Operation(summary = "Create category", description = "Create a new category")
    public ResponseEntity<CategoryResponse> create(
            @Valid @RequestBody CategoryRequest req,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        CategoryResponse resp = categoryService.createCategory(req, currentUser.getUser());
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Update an existing category")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest req,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        CategoryResponse resp = categoryService.updateCategory(id, req, currentUser.getUser());
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Deactivate (soft-delete) a category")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

        categoryService.deleteCategory(id, currentUser.getUser());
        return ResponseEntity.noContent().build();
    }
}
