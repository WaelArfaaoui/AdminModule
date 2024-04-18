package com.talan.AdminModule.repository;

import com.talan.AdminModule.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Integer> {
}

