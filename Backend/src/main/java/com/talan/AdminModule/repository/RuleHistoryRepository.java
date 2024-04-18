package com.talan.AdminModule.repository;

import com.talan.AdminModule.entity.Category;
import com.talan.AdminModule.entity.RuleHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleHistoryRepository extends JpaRepository<RuleHistory, Integer> {
}
