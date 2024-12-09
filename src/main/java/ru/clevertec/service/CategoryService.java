package ru.clevertec.service;

import org.hibernate.Hibernate;
import ru.clevertec.entity.Category;
import ru.clevertec.repository.impl.CategoryRepository;

import java.util.List;

public class CategoryService {

    private final CategoryRepository categoryRepository = new CategoryRepository();

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCar(Category category) {
        categoryRepository.update(category);
    }

    public void deleteCar(Category category) {
        categoryRepository.delete(category);
    }

    public Category getCarById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getAllCars() {
        return categoryRepository.findAll();
    }

    public Category findCategoryWithCars(Long categoryId) {
        return categoryRepository.findCategoryWithCars(categoryId);
    }

    public Category findCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId);
        if (category != null) {
            Hibernate.initialize(category.getCars());
        }
        return category;
    }
}
