package com.talan.AdminModule.service.impl;
import com.talan.AdminModule.dto.AttributeDataDto;
import com.talan.AdminModule.dto.AttributeDto;
import com.talan.AdminModule.dto.RuleDto;
import com.talan.AdminModule.dto.RuleModificationDto;
import com.talan.AdminModule.entity.*;
import com.talan.AdminModule.repository.*;
import com.talan.AdminModule.service.RuleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleServiceImpl implements RuleService {

    private final RuleRepository ruleRepository;
    private final AttributeRepository attributeRepository;
    private final CategoryRepository categoryRepository;
    private final RuleModificationRepository ruleModificationRepository ;
    private final RuleAttributeRepository  ruleAttributeRepository ;

    @Autowired
    public RuleServiceImpl(RuleRepository ruleRepository, AttributeRepository attributeRepository, CategoryRepository categoryRepository, RuleModificationRepository ruleModificationRepository, RuleAttributeRepository ruleAttributeRepository) {
        this.ruleRepository = ruleRepository;
        this.attributeRepository = attributeRepository;
        this.categoryRepository = categoryRepository;
        this.ruleModificationRepository = ruleModificationRepository;
        this.ruleAttributeRepository = ruleAttributeRepository;
    }

    @Override
    @Transactional
    public RuleDto save(RuleDto ruleDto) {
        Rule rule = new Rule();
        rule.setName(ruleDto.getName());
        rule.setDescription(ruleDto.getDescription());
        Category category = categoryRepository.findByName(ruleDto.getCategory().getName());
        if (category == null) {
            Category newCategory = new Category() ;
            newCategory.setName(ruleDto.getCategory().getName());
            category = categoryRepository.save(newCategory);
        }
        rule.setCategory(category);
        rule = ruleRepository.save(rule);
        RuleModification ruleModification = new RuleModification();
        ruleModification.setRule(rule);
        ruleModification.setModificationDate(rule.getLastModified());
        ruleModification.setModifiedBy(rule.getCreatedBy());
        ruleModification.setRuleName(rule.getName());
        ruleModification.setModificationDescription("Rule created");
        this.ruleModificationRepository.save(ruleModification) ;
        List<RuleAttribute> ruleAttributes = new ArrayList<>();
        for (AttributeDataDto attributeDto : ruleDto.getAttributeDtos()) {
            Attribute attribute = attributeRepository.findByNameIgnoreCase(attributeDto.getName().getName());
            if (attribute == null) {
                // Create a new attribute if it doesn't exist
                attribute = new Attribute();
                attribute.setName(attributeDto.getName().getName());
                // Set other properties of the attribute if needed
                attribute = attributeRepository.save(attribute);
            }

            // Create RuleAttribute and associate with rule and attribute
            RuleAttribute ruleAttribute = new RuleAttribute();
            ruleAttribute.setRule(rule);
            ruleAttribute.setAttribute(attribute);
            ruleAttribute.setPercentage(attributeDto.getPercentage());
            ruleAttribute.setValue(attributeDto.getValue());
            ruleAttributes.add(ruleAttribute);
        }

        // Save the list of RuleAttributes
        rule.setRuleAttributes(ruleAttributes);

        return RuleDto.fromEntity(rule);
    }

    @Override
    @Transactional
    public RuleDto updateStatus(Integer id, boolean enabled) {
        Rule rule = ruleRepository.findById(id).orElse(null);
        if (rule != null) {
            rule.setEnabled(enabled);
            rule = ruleRepository.save(rule);
            return RuleDto.fromEntity(rule);
        }
        return null; // or throw an exception
    }
    @Override
    @Transactional
    public RuleDto updateRule(Integer id, RuleDto updatedRuleDto, String modDescription, Integer modifiedBy){
        Rule existingRule = ruleRepository.findById(id).orElse(null);
        if (existingRule != null) {
            existingRule.setName(updatedRuleDto.getName());
            existingRule.setDescription(updatedRuleDto.getDescription());
            existingRule.setEnabled(updatedRuleDto.isEnabled());

            Category category = categoryRepository.findByName(updatedRuleDto.getCategory().getName());
            if (category == null) {
                category = new Category();
                category.setName(updatedRuleDto.getCategory().getName());
                category = categoryRepository.save(category);
            }
            existingRule.setCategory(category);

            // Delete existing RuleAttributes related to the rule
            ruleAttributeRepository.deleteByRule(existingRule);

            List<RuleAttribute> updatedRuleAttributes = new ArrayList<>();
            for (AttributeDataDto attributeDto : updatedRuleDto.getAttributeDtos()) {
                Attribute attribute = attributeRepository.findByNameIgnoreCase(attributeDto.getName().getName());
                if (attribute == null) {
                    attribute = new Attribute();
                    attribute.setName(attributeDto.getName().getName());
                    attribute = attributeRepository.save(attribute);
                }
                RuleAttribute ruleAttribute = new RuleAttribute();
                ruleAttribute.setRule(existingRule);
                ruleAttribute.setAttribute(attribute);
                ruleAttribute.setPercentage(attributeDto.getPercentage());
                ruleAttribute.setValue(attributeDto.getValue());
                updatedRuleAttributes.add(ruleAttribute);
            }
            existingRule.setRuleAttributes(updatedRuleAttributes);
            existingRule = ruleRepository.save(existingRule);
            System.out.println(existingRule.getLastModifiedBy());
            RuleModification ruleModification = new RuleModification();
            ruleModification.setRule(existingRule);
            ruleModification.setModificationDate(existingRule.getLastModified());
            ruleModification.setModifiedBy(existingRule.getLastModifiedBy());
            ruleModification.setRuleName(existingRule.getName());
            ruleModification.setModificationDescription(modDescription);
            this.ruleModificationRepository.save(ruleModification) ;

            return RuleDto.fromEntity(existingRule);
        }
        return null;
    }



    @Override
    @Transactional
    public void delete(Integer id) {
        ruleRepository.deleteById(id);
    }

    @Override
    public RuleDto findById(Integer id) {
        return ruleRepository.findByIdWithAttributes(id).map(RuleDto::fromEntity).orElse(null);
    }

    @Override
    public Page<RuleDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ruleRepository.findAll(pageable).map(RuleDto::fromEntity);
    }

    @Override
    public List<RuleModificationDto> getModificationsByRuleId(Integer id) {
        Rule rule = ruleRepository.findById(id).orElse(null);
        if (rule != null) {
            List<RuleModification> modifications = ruleModificationRepository.findByRuleOrderByModificationDateDesc(rule);
            List<RuleModificationDto> modificationDtos = modifications.stream()
                    .map(RuleModificationDto::fromEntity)
                    .collect(Collectors.toList());
            return modificationDtos ;
        }
        return null;
    }
}
