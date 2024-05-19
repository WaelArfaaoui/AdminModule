package com.talan.adminmodule.service.impl;

import com.talan.adminmodule.dto.CategoryDto;
import com.talan.adminmodule.entity.Category;
import com.talan.adminmodule.repository.CategoryRepository;
import com.talan.adminmodule.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        return CategoryDto.fromEntity(categoryRepository.save(CategoryDto.toEntity(categoryDto)));
    }

    @Override
    public void delete(Integer id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto findById(Integer id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "No category with ID = " + id + " found in the database")
        );
        return CategoryDto.fromEntity(category);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .toList();
    }
    @Override
    public List<CategoryDto> getTopUsedCategories() {
        List<Object[]> categoryObjects = categoryRepository.findTopUsedCategoriesWithRuleCount(PageRequest.of(0, 5));
        List<CategoryDto> categories = new ArrayList<>();

        for (Object[] categoryObject : categoryObjects) {
            Category category = (Category) categoryObject[0];
            Long ruleCount = (Long) categoryObject[1];
            CategoryDto categoryDto = CategoryDto.fromEntity(category);
            categoryDto.setRuleCount(ruleCount.intValue());
            categories.add(categoryDto);
        }

        return categories;
    }
}
