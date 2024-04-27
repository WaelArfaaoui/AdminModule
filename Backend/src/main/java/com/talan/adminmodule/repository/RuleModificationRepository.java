package com.talan.adminmodule.repository;

import com.talan.adminmodule.entity.Rule;
import com.talan.adminmodule.entity.RuleModification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleModificationRepository extends JpaRepository<RuleModification, Integer> {
    List<RuleModification> findByRuleOrderByModificationDateDesc(Rule rule);
}
