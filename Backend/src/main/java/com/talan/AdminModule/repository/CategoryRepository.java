package com.talan.AdminModule.repository;

import com.talan.AdminModule.entity.Category;
import com.talan.AdminModule.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
    boolean existsByName(String name) ;
}
