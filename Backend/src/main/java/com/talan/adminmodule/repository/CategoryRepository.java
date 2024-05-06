package com.talan.adminmodule.repository;

import com.talan.adminmodule.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
    boolean existsByName(String name) ;
}
