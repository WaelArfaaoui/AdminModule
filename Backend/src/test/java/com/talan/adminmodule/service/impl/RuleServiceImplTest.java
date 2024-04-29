package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.talan.adminmodule.dto.AttributeDataDto;
import com.talan.adminmodule.dto.AttributeDto;
import com.talan.adminmodule.entity.Attribute;
import com.talan.adminmodule.entity.Category;
import com.talan.adminmodule.entity.Rule;
import com.talan.adminmodule.entity.RuleAttribute;
import com.talan.adminmodule.repository.AttributeRepository;
import com.talan.adminmodule.repository.CategoryRepository;
import com.talan.adminmodule.repository.RuleAttributeRepository;
import com.talan.adminmodule.repository.RuleModificationRepository;
import com.talan.adminmodule.repository.RuleRepository;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RuleServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RuleServiceImplTest {
    @MockBean
    private AttributeRepository attributeRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private RuleAttributeRepository ruleAttributeRepository;

    @MockBean
    private RuleModificationRepository ruleModificationRepository;

    @MockBean
    private RuleRepository ruleRepository;

    @Autowired
    private RuleServiceImpl ruleServiceImpl;

    /**
     * Method under test: {@link RuleServiceImpl#addRuleAttribute(Attribute, Rule, AttributeDataDto)}
     */
    @Test
    void testAddRuleAttribute() {
        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule existingRule = new Rule();
        existingRule.setCategory(category);
        existingRule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        existingRule.setCreatedBy(1);
        existingRule.setDescription("The characteristics of someone or something");
        existingRule.setEnabled(true);
        existingRule.setId(1);
        existingRule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        existingRule.setLastModifiedBy(1);
        existingRule.setName("Name");
        existingRule.setRuleAttributes(new ArrayList<>());
        existingRule.setRuleModifications(new ArrayList<>());
        AttributeDataDto.AttributeDataDtoBuilder idResult = AttributeDataDto.builder().id(1);
        RuleAttribute actualAddRuleAttributeResult = ruleServiceImpl.addRuleAttribute(attribute, existingRule,
                idResult.name(AttributeDto.builder().id(1).name("Name").build()).percentage(10.0d).value(10.0d).build());
        assertSame(attribute, actualAddRuleAttributeResult.getAttribute());
        assertEquals(10.0d, actualAddRuleAttributeResult.getValue().doubleValue());
        assertSame(existingRule, actualAddRuleAttributeResult.getRule());
        assertEquals(10.0d, actualAddRuleAttributeResult.getPercentage().doubleValue());
    }
}

