package com.talan.AdminModule.service;

import com.talan.AdminModule.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryDto categoryDto);

    void delete(Integer id);

    CategoryDto findById(Integer id);

    List<CategoryDto> findAll();

    boolean existByName(String name);
}