package com.talan.adminmodule.service.impl;

import com.talan.adminmodule.dto.*;
import com.talan.adminmodule.entity.*;
import com.talan.adminmodule.exception.EntityNotFoundException;
import com.talan.adminmodule.exception.ErrorCodes;
import com.talan.adminmodule.exception.InvalidEntityException;
import com.talan.adminmodule.repository.*;
import com.talan.adminmodule.service.RuleService;
import com.talan.adminmodule.validator.RuleValidator;
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
    private final RuleModificationRepository ruleModificationRepository ;
    private final RuleAttributeRepository  ruleAttributeRepository ;
    private String ruleMessage = "Rule with ID :" ;
    private String ruleNotFound = "not found" ;
    

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
        List<String> errors = RuleValidator.validate(ruleDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Rule is not valid", ErrorCodes.RULE_NOT_VALID, errors);
        }

        Rule rule = new Rule();
        rule.setName(ruleDto.getName());
        rule.setDescription(ruleDto.getDescription());
        Category category = categoryRepository.findByName(ruleDto.getCategory().getName());
        if (category == null) {
            Category newCategory = new Category();
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
        this.ruleModificationRepository.save(ruleModification);
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
        if (rule == null) {
            throw new EntityNotFoundException(ruleMessage + id + ruleNotFound, ErrorCodes.RULE_NOT_FOUND);
        }

        rule.setEnabled(enabled);
        rule = ruleRepository.save(rule);
        return RuleDto.fromEntity(rule);
    }

    @Override
    @Transactional
    public RuleDto updateRule(Integer id, RuleDto updatedRuleDto, String modDescription, Integer modifiedBy) {
        Rule existingRule = ruleRepository.findById(id).orElse(null);
        if (existingRule == null) {
            throw new EntityNotFoundException(ruleMessage + id + ruleNotFound, ErrorCodes.RULE_NOT_FOUND);
        }

        List<String> errors = RuleValidator.validate(updatedRuleDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Updated rule is not valid", ErrorCodes.RULE_NOT_VALID, errors);
        }

        existingRule.setName(updatedRuleDto.getName());
        existingRule.setDescription(updatedRuleDto.getDescription());
        Category category = categoryRepository.findByName(updatedRuleDto.getCategory().getName());
        if (category == null) {
            category = CategoryDto.toEntity(updatedRuleDto.getCategory());
            category = categoryRepository.save(category);
        }
        existingRule.setCategory(category);
        ruleAttributeRepository.deleteByRule(existingRule);
        List<RuleAttribute> updatedRuleAttributes = new ArrayList<>();
        for (AttributeDataDto attributeDto : updatedRuleDto.getAttributeDtos()) {
            Attribute attribute = attributeRepository.findByNameIgnoreCase(attributeDto.getName().getName());
            if (attribute == null) {
                attribute = AttributeDto.toEntity(attributeDto.getName());
                attribute = attributeRepository.save(attribute);
            }
            RuleAttribute ruleAttribute = this.addRuleAttribute(attribute, existingRule, attributeDto);
            updatedRuleAttributes.add(ruleAttribute);
        }
        existingRule.setRuleAttributes(updatedRuleAttributes);
        existingRule = ruleRepository.save(existingRule);
        this.saveRuleModification(existingRule, modDescription);
        return RuleDto.fromEntity(existingRule);
    }

    public void saveRuleModification(Rule existingRule, String modDescription) {
        RuleModification ruleModification = new RuleModification();
        ruleModification.setRule(existingRule);
        ruleModification.setModificationDate(existingRule.getLastModified());
        ruleModification.setModifiedBy(existingRule.getLastModifiedBy());
        ruleModification.setRuleName(existingRule.getName());
        ruleModification.setModificationDescription(modDescription);
        this.ruleModificationRepository.save(ruleModification);
    }

    public RuleAttribute addRuleAttribute(Attribute attribute, Rule existingRule, AttributeDataDto attributeDto) {
        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setRule(existingRule);
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setPercentage(attributeDto.getPercentage());
        ruleAttribute.setValue(attributeDto.getValue());
        return ruleAttribute;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Rule rule = ruleRepository.findById(id).orElse(null);
        if (rule == null) {
            throw new EntityNotFoundException(ruleMessage + id + ruleNotFound, ErrorCodes.RULE_NOT_FOUND);
        }
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
        if (rule == null) {
            throw new EntityNotFoundException(ruleMessage + id + ruleNotFound, ErrorCodes.RULE_NOT_FOUND);
        }

        List<RuleModification> modifications = ruleModificationRepository.findByRuleOrderByModificationDateDesc(rule);
        return modifications.stream()
                .map(RuleModificationDto::fromEntity)
                .toList();
    }



    @Override
    public Page<RuleDto> searchRules(int page, int size, String query) {
        Pageable pageable = PageRequest.of(page, size);
        return ruleRepository.search(query, pageable).map(RuleDto::fromEntity);
    }
}
