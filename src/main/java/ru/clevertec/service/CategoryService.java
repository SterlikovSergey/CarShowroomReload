package ru.clevertec.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.entity.Category;
import ru.clevertec.exception.CategoryNotFoundException;
import ru.clevertec.repository.CategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, Category updatedCategory) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
        category.setName(updatedCategory.getName());
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
