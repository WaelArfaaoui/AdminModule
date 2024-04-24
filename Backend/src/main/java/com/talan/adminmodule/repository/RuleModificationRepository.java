package com.talan.AdminModule.repository;

import com.talan.AdminModule.entity.Rule;
import com.talan.AdminModule.entity.RuleModification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleModificationRepository extends JpaRepository<RuleModification, Integer> {
    List<RuleModification> findByRuleOrderByModificationDateDesc(Rule rule);
}
