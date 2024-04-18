package com.talan.AdminModule.service;

import com.talan.AdminModule.dto.AttributeDto;
import com.talan.AdminModule.dto.RuleDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RuleService {

    RuleDto save(RuleDto ruleDTO );

    RuleDto updateStatus(Integer id, boolean enabled);

    @Transactional
    RuleDto updateRule(Integer id, RuleDto updatedRuleDto);

    void delete(Integer id);

    RuleDto findById(Integer id);

    Page<RuleDto> findAll(int page, int size);
}
