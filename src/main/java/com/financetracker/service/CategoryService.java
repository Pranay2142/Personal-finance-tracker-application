package com.financetracker.service;

import com.financetracker.dto.request.CategoryRequest;
import com.financetracker.dto.response.CategoryResponse;
import com.financetracker.entity.Category;
import com.financetracker.entity.User;
import com.financetracker.exception.BusinessLogicException;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> listCategories(User user) {
        return categoryRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse createCategory(CategoryRequest req, User user) {
        if (categoryRepository.existsByUserAndNameAndType(user, req.getName(), req.getType())) {
            throw new BusinessLogicException("Category already exists");
        }

        Category cat = new Category();
        cat.setUser(user);
        cat.setName(req.getName());
        cat.setType(req.getType());
        cat.setColor(req.getColor());
        cat.setIcon(req.getIcon());
        cat.setIsActive(true);

        return toResponse(categoryRepository.save(cat));
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest req, User user) {
        Category cat = categoryRepository.findById(id)
                .filter(c -> c.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (!cat.getName().equals(req.getName()) || !cat.getType().equals(req.getType())) {
            if (categoryRepository.existsByUserAndNameAndType(user, req.getName(), req.getType())) {
                throw new BusinessLogicException("Category name & type already in use");
            }
        }

        cat.setName(req.getName());
        cat.setType(req.getType());
        cat.setColor(req.getColor());
        cat.setIcon(req.getIcon());

        return toResponse(categoryRepository.save(cat));
    }

    public void deleteCategory(Long id, User user) {
        Category cat = categoryRepository.findById(id)
                .filter(c -> c.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        cat.setIsActive(false);
        categoryRepository.save(cat);
    }

    private CategoryResponse toResponse(Category cat) {
        CategoryResponse resp = new CategoryResponse();
        resp.setId(cat.getId());
        resp.setName(cat.getName());
        resp.setType(cat.getType());
        resp.setColor(cat.getColor());
        resp.setIcon(cat.getIcon());
        resp.setIsActive(cat.getIsActive());
        resp.setCreatedAt(cat.getCreatedAt());
        return resp;
    }
}
