package com.talan.AdminModule.service;

import com.talan.AdminModule.dto.RuleDto;
import com.talan.AdminModule.dto.RuleModificationDto;
import org.springframework.data.domain.Page;
import jakarta.transaction.Transactional;
import java.util.List;

public interface RuleService {

    RuleDto save(RuleDto ruleDto);

    RuleDto updateStatus(Integer id, boolean enabled);

    @Transactional
    RuleDto updateRule(Integer id, RuleDto updatedRuleDto, String modDescription, Integer modifiedBy);

    void delete(Integer id);

    RuleDto findById(Integer id);

    Page<RuleDto> findAll(int page, int size);

    List<RuleModificationDto> getModificationsByRuleId(Integer id);

}
