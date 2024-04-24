package com.talan.AdminModule.repository;

import com.talan.AdminModule.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RuleRepository extends JpaRepository<Rule, Integer> {
    @Query("SELECT r FROM Rule r LEFT JOIN FETCH r.ruleAttributes ra WHERE r.id = :id")
    Optional<Rule> findByIdWithAttributes(@Param("id") Integer id);
}

