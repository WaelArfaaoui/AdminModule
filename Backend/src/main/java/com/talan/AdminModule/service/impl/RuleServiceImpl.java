package com.talan.AdminModule.service.impl;
import com.talan.AdminModule.dto.AttributeDto;
import com.talan.AdminModule.dto.RuleDto;
import com.talan.AdminModule.entity.*;
import com.talan.AdminModule.repository.AttributeRepository;
import com.talan.AdminModule.repository.CategoryRepository;
import com.talan.AdminModule.repository.RuleRepository;
import com.talan.AdminModule.service.RuleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    private final RuleRepository ruleRepository;
    private final AttributeRepository attributeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public RuleServiceImpl(RuleRepository ruleRepository, AttributeRepository attributeRepository, CategoryRepository categoryRepository) {
        this.ruleRepository = ruleRepository;
        this.attributeRepository = attributeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public RuleDto save(RuleDto ruleDto) {
        Rule rule = new Rule();
        rule.setName(ruleDto.getName());
        rule.setDescription(ruleDto.getDescription());

        // Check if the category exists by name
        Category category = categoryRepository.findByName(ruleDto.getCategory());
        if (category == null) {
            // If category doesn't exist, create it
            category = new Category();
            category.setName(ruleDto.getCategory());
            category = categoryRepository.save(category);
        }

        rule.setCategory(category);

        rule = ruleRepository.save(rule); // Save the rule to get its ID

        List<RuleAttribute> ruleAttributes = new ArrayList<>();
        for (AttributeDto attributeDto : ruleDto.getAttributeDtos()) {
            Attribute attribute = attributeRepository.findByNameIgnoreCase(attributeDto.getName());
            if (attribute == null) {
                // Create a new attribute if it doesn't exist
                attribute = new Attribute();
                attribute.setName(attributeDto.getName());
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
    public RuleDto updateRule(Integer id, RuleDto updatedRuleDto) {
        Rule existingRule = ruleRepository.findById(id).orElse(null);
        if (existingRule != null) {
            existingRule.setName(updatedRuleDto.getName());
            existingRule.setDescription(updatedRuleDto.getDescription());
            existingRule.setEnabled(updatedRuleDto.isEnabled());

            Category category = categoryRepository.findByName(updatedRuleDto.getCategory());
            if (category == null) {
                category = new Category();
                category.setName(updatedRuleDto.getCategory());
                category = categoryRepository.save(category);
            }
            existingRule.setCategory(category);
            List<RuleAttribute> updatedRuleAttributes = new ArrayList<>();
            for (AttributeDto attributeDto : updatedRuleDto.getAttributeDtos()) {
                Attribute attribute = attributeRepository.findByNameIgnoreCase(attributeDto.getName());
                if (attribute == null) {
                    attribute = new Attribute();
                    attribute.setName(attributeDto.getName());
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
            RuleModification ruleModification = new RuleModification();
            ruleModification.setRule(existingRule);
            ruleModification.setModificationDate(existingRule.getLastModified());
            ruleModification.setModifiedBy(existingRule.getLastModifiedBy());
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
        return ruleRepository.findById(id).map(RuleDto::fromEntity).orElse(null);
    }

    @Override
    public Page<RuleDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ruleRepository.findAll(pageable).map(RuleDto::fromEntity);
    }
}
