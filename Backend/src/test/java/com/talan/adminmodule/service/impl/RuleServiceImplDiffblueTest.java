package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.talan.adminmodule.dto.AttributeDataDto;
import com.talan.adminmodule.dto.AttributeDto;
import com.talan.adminmodule.dto.CategoryDto;
import com.talan.adminmodule.dto.RuleDto;
import com.talan.adminmodule.dto.RuleModificationDto;
import com.talan.adminmodule.entity.Attribute;
import com.talan.adminmodule.entity.Category;
import com.talan.adminmodule.entity.Rule;
import com.talan.adminmodule.entity.RuleAttribute;
import com.talan.adminmodule.entity.RuleModification;
import com.talan.adminmodule.config.exception.EntityNotFoundException;
import com.talan.adminmodule.config.exception.InvalidEntityException;
import com.talan.adminmodule.repository.AttributeRepository;
import com.talan.adminmodule.repository.CategoryRepository;
import com.talan.adminmodule.repository.RuleAttributeRepository;
import com.talan.adminmodule.repository.RuleModificationRepository;
import com.talan.adminmodule.repository.RuleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RuleServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RuleServiceImplDiffblueTest {
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
     * Method under test: {@link RuleServiceImpl#save(RuleDto)}
     */
    @Test
    void testSave() {
        // Arrange, Act and Assert
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.save(null));
    }

    /**
     * Method under test: {@link RuleServiceImpl#save(RuleDto)}
     */
    @Test
    void testSave2() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());
        when(categoryRepository.findByName(Mockito.<String>any())).thenReturn(category2);
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category3);

        Category category4 = new Category();
        category4.setId(1);
        category4.setName("Name");
        category4.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category4);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());

        RuleModification ruleModification = new RuleModification();
        ruleModification.setId(1);
        ruleModification.setModificationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ruleModification.setModificationDescription("Modification Description");
        ruleModification.setModifiedBy(1);
        ruleModification.setRule(rule2);
        ruleModification.setRuleName("Rule Name");
        when(ruleModificationRepository.save(Mockito.<RuleModification>any())).thenReturn(ruleModification);
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        CategoryDto category5 = CategoryDto.builder().id(1).name("Name").build();
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult.category(category5);
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto ruleDto = idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay())
                .lastModifiedBy(1)
                .name("Name")
                .build();

        // Act
        RuleDto actualSaveResult = ruleServiceImpl.save(ruleDto);

        // Assert
        verify(categoryRepository).findByName("Name");
        verify(ruleRepository).save(isA(Rule.class));
        verify(ruleModificationRepository).save(isA(RuleModification.class));
        LocalTime expectedToLocalTimeResult = actualSaveResult.getLastModified().toLocalTime();
        assertSame(expectedToLocalTimeResult, actualSaveResult.getCreateDate().toLocalTime());
    }

    /**
     * Method under test: {@link RuleServiceImpl#save(RuleDto)}
     */
    @Test
    void testSave3() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());
        when(categoryRepository.findByName(Mockito.<String>any())).thenReturn(category2);
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category3);
        when(ruleModificationRepository.save(Mockito.<RuleModification>any()))
                .thenThrow(new InvalidEntityException("An error occurred"));
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        CategoryDto category4 = CategoryDto.builder().id(1).name("Name").build();
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult.category(category4);
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto ruleDto = idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay())
                .lastModifiedBy(1)
                .name("Name")
                .build();

        // Act and Assert
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.save(ruleDto));
        verify(categoryRepository).findByName("Name");
        verify(ruleRepository).save(isA(Rule.class));
        verify(ruleModificationRepository).save(isA(RuleModification.class));
    }

    /**
     * Method under test: {@link RuleServiceImpl#save(RuleDto)}
     */
    @Test
    void testSave4() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category3);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule);
        ruleAttribute.setValue(10.0d);

        ArrayList<RuleAttribute> ruleAttributeList = new ArrayList<>();
        ruleAttributeList.add(ruleAttribute);
        Rule rule2 = mock(Rule.class);
        when(rule2.isEnabled()).thenReturn(true);
        when(rule2.getCategory()).thenReturn(category2);
        when(rule2.getCreatedBy()).thenReturn(1);
        when(rule2.getId()).thenReturn(1);
        when(rule2.getLastModifiedBy()).thenReturn(1);
        when(rule2.getDescription()).thenReturn("The characteristics of someone or something");
        when(rule2.getName()).thenReturn("Name");
        when(rule2.getCreateDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule2.getLastModified()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule2.getRuleAttributes()).thenReturn(ruleAttributeList);
        doNothing().when(rule2).setCategory(Mockito.<Category>any());
        doNothing().when(rule2).setCreateDate(Mockito.<LocalDateTime>any());
        doNothing().when(rule2).setCreatedBy(Mockito.<Integer>any());
        doNothing().when(rule2).setDescription(Mockito.<String>any());
        doNothing().when(rule2).setEnabled(anyBoolean());
        doNothing().when(rule2).setId(Mockito.<Integer>any());
        doNothing().when(rule2).setLastModified(Mockito.<LocalDateTime>any());
        doNothing().when(rule2).setLastModifiedBy(Mockito.<Integer>any());
        doNothing().when(rule2).setName(Mockito.<String>any());
        doNothing().when(rule2).setRuleAttributes(Mockito.<List<RuleAttribute>>any());
        doNothing().when(rule2).setRuleModifications(Mockito.<List<RuleModification>>any());
        rule2.setCategory(category);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule2);

        Category category4 = new Category();
        category4.setId(1);
        category4.setName("Name");
        category4.setRules(new ArrayList<>());

        Category category5 = new Category();
        category5.setId(1);
        category5.setName("Name");
        category5.setRules(new ArrayList<>());
        when(categoryRepository.findByName(Mockito.<String>any())).thenReturn(category4);
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category5);

        Category category6 = new Category();
        category6.setId(1);
        category6.setName("Name");
        category6.setRules(new ArrayList<>());

        Rule rule3 = new Rule();
        rule3.setCategory(category6);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("The characteristics of someone or something");
        rule3.setEnabled(true);
        rule3.setId(1);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("Name");
        rule3.setRuleAttributes(new ArrayList<>());
        rule3.setRuleModifications(new ArrayList<>());

        RuleModification ruleModification = new RuleModification();
        ruleModification.setId(1);
        ruleModification.setModificationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ruleModification.setModificationDescription("Modification Description");
        ruleModification.setModifiedBy(1);
        ruleModification.setRule(rule3);
        ruleModification.setRuleName("Rule Name");
        when(ruleModificationRepository.save(Mockito.<RuleModification>any())).thenReturn(ruleModification);
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        CategoryDto category7 = CategoryDto.builder().id(1).name("Name").build();
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult.category(category7);
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto ruleDto = idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay())
                .lastModifiedBy(1)
                .name("Name")
                .build();

        // Act
        RuleDto actualSaveResult = ruleServiceImpl.save(ruleDto);

        // Assert
        verify(rule2).getCategory();
        verify(rule2).getCreateDate();
        verify(rule2, atLeast(1)).getCreatedBy();
        verify(rule2).getDescription();
        verify(rule2).getId();
        verify(rule2, atLeast(1)).getLastModified();
        verify(rule2).getLastModifiedBy();
        verify(rule2, atLeast(1)).getName();
        verify(rule2).getRuleAttributes();
        verify(rule2).isEnabled();
        verify(rule2).setCategory(isA(Category.class));
        verify(rule2).setCreateDate(isA(LocalDateTime.class));
        verify(rule2).setCreatedBy(1);
        verify(rule2).setDescription("The characteristics of someone or something");
        verify(rule2).setEnabled(true);
        verify(rule2).setId(1);
        verify(rule2).setLastModified(isA(LocalDateTime.class));
        verify(rule2).setLastModifiedBy(1);
        verify(rule2).setName("Name");
        verify(rule2, atLeast(1)).setRuleAttributes(isA(List.class));
        verify(rule2).setRuleModifications(isA(List.class));
        verify(categoryRepository).findByName("Name");
        verify(ruleRepository).save(isA(Rule.class));
        verify(ruleModificationRepository).save(isA(RuleModification.class));
        LocalTime expectedToLocalTimeResult = actualSaveResult.getLastModified().toLocalTime();
        assertSame(expectedToLocalTimeResult, actualSaveResult.getCreateDate().toLocalTime());
    }

    /**
     * Method under test: {@link RuleServiceImpl#save(RuleDto)}
     */

    @Test
    void testUpdateStatus() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category2);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule2);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        RuleDto actualUpdateStatusResult = ruleServiceImpl.updateStatus(1, true);

        // Assert
        verify(ruleRepository).findById(1);
        verify(ruleRepository).save(isA(Rule.class));
        LocalTime expectedToLocalTimeResult = actualUpdateStatusResult.getLastModified().toLocalTime();
        assertSame(expectedToLocalTimeResult, actualUpdateStatusResult.getCreateDate().toLocalTime());
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateStatus(Integer, boolean)}
     */
    @Test
    void testUpdateStatus2() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);
        when(ruleRepository.save(Mockito.<Rule>any())).thenThrow(new InvalidEntityException("An error occurred"));
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.updateStatus(1, true));
        verify(ruleRepository).findById(1);
        verify(ruleRepository).save(isA(Rule.class));
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateStatus(Integer, boolean)}
     */
    @Test
    void testUpdateStatus3() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category4 = new Category();
        category4.setId(1);
        category4.setName("Name");
        category4.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category4);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule2);
        ruleAttribute.setValue(10.0d);

        ArrayList<RuleAttribute> ruleAttributeList = new ArrayList<>();
        ruleAttributeList.add(ruleAttribute);
        Rule rule3 = mock(Rule.class);
        when(rule3.isEnabled()).thenReturn(true);
        when(rule3.getCategory()).thenReturn(category3);
        when(rule3.getCreatedBy()).thenReturn(1);
        when(rule3.getId()).thenReturn(1);
        when(rule3.getLastModifiedBy()).thenReturn(1);
        when(rule3.getDescription()).thenReturn("The characteristics of someone or something");
        when(rule3.getName()).thenReturn("Name");
        when(rule3.getCreateDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule3.getLastModified()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule3.getRuleAttributes()).thenReturn(ruleAttributeList);
        doNothing().when(rule3).setCategory(Mockito.<Category>any());
        doNothing().when(rule3).setCreateDate(Mockito.<LocalDateTime>any());
        doNothing().when(rule3).setCreatedBy(Mockito.<Integer>any());
        doNothing().when(rule3).setDescription(Mockito.<String>any());
        doNothing().when(rule3).setEnabled(anyBoolean());
        doNothing().when(rule3).setId(Mockito.<Integer>any());
        doNothing().when(rule3).setLastModified(Mockito.<LocalDateTime>any());
        doNothing().when(rule3).setLastModifiedBy(Mockito.<Integer>any());
        doNothing().when(rule3).setName(Mockito.<String>any());
        doNothing().when(rule3).setRuleAttributes(Mockito.<List<RuleAttribute>>any());
        doNothing().when(rule3).setRuleModifications(Mockito.<List<RuleModification>>any());
        rule3.setCategory(category2);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("The characteristics of someone or something");
        rule3.setEnabled(true);
        rule3.setId(1);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("Name");
        rule3.setRuleAttributes(new ArrayList<>());
        rule3.setRuleModifications(new ArrayList<>());
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule3);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        RuleDto actualUpdateStatusResult = ruleServiceImpl.updateStatus(1, true);

        // Assert
        verify(rule3).getCategory();
        verify(rule3).getCreateDate();
        verify(rule3).getCreatedBy();
        verify(rule3).getDescription();
        verify(rule3).getId();
        verify(rule3).getLastModified();
        verify(rule3).getLastModifiedBy();
        verify(rule3).getName();
        verify(rule3).getRuleAttributes();
        verify(rule3).isEnabled();
        verify(rule3).setCategory(isA(Category.class));
        verify(rule3).setCreateDate(isA(LocalDateTime.class));
        verify(rule3).setCreatedBy(1);
        verify(rule3).setDescription("The characteristics of someone or something");
        verify(rule3).setEnabled(true);
        verify(rule3).setId(1);
        verify(rule3).setLastModified(isA(LocalDateTime.class));
        verify(rule3).setLastModifiedBy(1);
        verify(rule3).setName("Name");
        verify(rule3).setRuleAttributes(isA(List.class));
        verify(rule3).setRuleModifications(isA(List.class));
        verify(ruleRepository).findById(1);
        verify(ruleRepository).save(isA(Rule.class));
        LocalTime expectedToLocalTimeResult = actualUpdateStatusResult.getLastModified().toLocalTime();
        assertSame(expectedToLocalTimeResult, actualUpdateStatusResult.getCreateDate().toLocalTime());
    }

    @Test
    void testUpdateStatus5() {
        // Arrange
        Optional<Rule> emptyResult = Optional.empty();
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());
        Rule rule = mock(Rule.class);
        doNothing().when(rule).setCategory(Mockito.<Category>any());
        doNothing().when(rule).setCreateDate(Mockito.<LocalDateTime>any());
        doNothing().when(rule).setCreatedBy(Mockito.<Integer>any());
        doNothing().when(rule).setDescription(Mockito.<String>any());
        doNothing().when(rule).setEnabled(anyBoolean());
        doNothing().when(rule).setId(Mockito.<Integer>any());
        doNothing().when(rule).setLastModified(Mockito.<LocalDateTime>any());
        doNothing().when(rule).setLastModifiedBy(Mockito.<Integer>any());
        doNothing().when(rule).setName(Mockito.<String>any());
        doNothing().when(rule).setRuleAttributes(Mockito.<List<RuleAttribute>>any());
        doNothing().when(rule).setRuleModifications(Mockito.<List<RuleModification>>any());
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> ruleServiceImpl.updateStatus(1, true));
        verify(rule).setCategory(isA(Category.class));
        verify(rule).setCreateDate(isA(LocalDateTime.class));
        verify(rule).setCreatedBy(1);
        verify(rule).setDescription("The characteristics of someone or something");
        verify(rule).setEnabled(true);
        verify(rule).setId(1);
        verify(rule).setLastModified(isA(LocalDateTime.class));
        verify(rule).setLastModifiedBy(1);
        verify(rule).setName("Name");
        verify(rule).setRuleAttributes(isA(List.class));
        verify(rule).setRuleModifications(isA(List.class));
        verify(ruleRepository).findById(1);
    }

    /**
     * Method under test:
     * {@link RuleServiceImpl#updateRule(Integer, RuleDto, String, Integer)}
     */
    @Test
    void testUpdateRule() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.updateRule(1, null, "Mod Description", 1));
        verify(ruleRepository).findById(1);
    }

    /**
     * Method under test:
     * {@link RuleServiceImpl#updateRule(Integer, RuleDto, String, Integer)}
     */
    @Test
    void testUpdateRule2() {
        // Arrange
        Optional<Rule> emptyResult = Optional.empty();
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> ruleServiceImpl.updateRule(1, null, "Mod Description", 1));
        verify(ruleRepository).findById(1);
    }

    /**
     * Method under test:
     * {@link RuleServiceImpl#updateRule(Integer, RuleDto, String, Integer)}
     */
    @Test
    void testUpdateRule3() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category2);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule2);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Category category4 = new Category();
        category4.setId(1);
        category4.setName("Name");
        category4.setRules(new ArrayList<>());
        when(categoryRepository.findByName(Mockito.<String>any())).thenReturn(category3);
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category4);

        Category category5 = new Category();
        category5.setId(1);
        category5.setName("Name");
        category5.setRules(new ArrayList<>());

        Rule rule3 = new Rule();
        rule3.setCategory(category5);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("The characteristics of someone or something");
        rule3.setEnabled(true);
        rule3.setId(1);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("Name");
        rule3.setRuleAttributes(new ArrayList<>());
        rule3.setRuleModifications(new ArrayList<>());

        RuleModification ruleModification = new RuleModification();
        ruleModification.setId(1);
        ruleModification.setModificationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ruleModification.setModificationDescription("Modification Description");
        ruleModification.setModifiedBy(1);
        ruleModification.setRule(rule3);
        ruleModification.setRuleName("Rule Name");
        when(ruleModificationRepository.save(Mockito.<RuleModification>any())).thenReturn(ruleModification);
        doNothing().when(ruleAttributeRepository).deleteByRule(Mockito.<Rule>any());
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        CategoryDto category6 = CategoryDto.builder().id(1).name("Name").build();
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult.category(category6);
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto updatedRuleDto = idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay())
                .lastModifiedBy(1)
                .name("Name")
                .build();

        // Act
        RuleDto actualUpdateRuleResult = ruleServiceImpl.updateRule(1, updatedRuleDto, "Mod Description", 1);

        // Assert
        verify(categoryRepository).findByName("Name");
        verify(ruleAttributeRepository).deleteByRule(isA(Rule.class));
        verify(ruleRepository).findById(1);
        verify(ruleRepository).save(isA(Rule.class));
        verify(ruleModificationRepository).save(isA(RuleModification.class));
        LocalTime expectedToLocalTimeResult = actualUpdateRuleResult.getLastModified().toLocalTime();
        assertSame(expectedToLocalTimeResult, actualUpdateRuleResult.getCreateDate().toLocalTime());
    }

    /**
     * Method under test:
     * {@link RuleServiceImpl#updateRule(Integer, RuleDto, String, Integer)}
     */
    @Test
    void testUpdateRule4() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());
        when(categoryRepository.findByName(Mockito.<String>any())).thenReturn(category2);
        doThrow(new InvalidEntityException("An error occurred")).when(ruleAttributeRepository)
                .deleteByRule(Mockito.<Rule>any());
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        CategoryDto category3 = CategoryDto.builder().id(1).name("Name").build();
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult.category(category3);
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto updatedRuleDto = idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay())
                .lastModifiedBy(1)
                .name("Name")
                .build();

        // Act and Assert
        assertThrows(InvalidEntityException.class,
                () -> ruleServiceImpl.updateRule(1, updatedRuleDto, "Mod Description", 1));
        verify(categoryRepository).findByName("Name");
        verify(ruleAttributeRepository).deleteByRule(isA(Rule.class));
        verify(ruleRepository).findById(1);
    }



    @Test
    void testSaveRuleModification() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        RuleModification ruleModification = new RuleModification();
        ruleModification.setId(1);
        ruleModification.setModificationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ruleModification.setModificationDescription("Modification Description");
        ruleModification.setModifiedBy(1);
        ruleModification.setRule(rule);
        ruleModification.setRuleName("Rule Name");
        when(ruleModificationRepository.save(Mockito.<RuleModification>any())).thenReturn(ruleModification);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Rule existingRule = new Rule();
        existingRule.setCategory(category2);
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

        // Act
        ruleServiceImpl.saveRuleModification(existingRule, "Mod Description");

        // Assert
        verify(ruleModificationRepository).save(isA(RuleModification.class));
    }

    /**
     * Method under test: {@link RuleServiceImpl#saveRuleModification(Rule, String)}
     */
    @Test
    void testSaveRuleModification2() {
        // Arrange
        when(ruleModificationRepository.save(Mockito.<RuleModification>any()))
                .thenThrow(new InvalidEntityException("An error occurred"));

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

        // Act and Assert
        assertThrows(InvalidEntityException.class,
                () -> ruleServiceImpl.saveRuleModification(existingRule, "Mod Description"));
        verify(ruleModificationRepository).save(isA(RuleModification.class));
    }

    /**
     * Method under test:
     * {@link RuleServiceImpl#addRuleAttribute(Attribute, Rule, AttributeDataDto)}
     */
    @Test
    void testAddRuleAttribute() {
        // Arrange
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
        AttributeDto name = AttributeDto.builder().id(1).name("Name").build();
        AttributeDataDto attributeDto = idResult.name(name).percentage(10.0d).value(10.0d).build();

        // Act and Assert
        Rule rule = ruleServiceImpl.addRuleAttribute(attribute, existingRule, attributeDto).getRule();
        LocalTime expectedToLocalTimeResult = rule.getLastModified().toLocalTime();
        assertSame(expectedToLocalTimeResult, rule.getCreateDate().toLocalTime());
    }

    /**
     * Method under test: {@link RuleServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);
        doNothing().when(ruleRepository).deleteById(Mockito.<Integer>any());
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        ruleServiceImpl.delete(1);

        // Assert that nothing has changed
        verify(ruleRepository).deleteById(1);
        verify(ruleRepository).findById(1);
    }

    /**
     * Method under test: {@link RuleServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete2() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);
        doThrow(new InvalidEntityException("An error occurred")).when(ruleRepository).deleteById(Mockito.<Integer>any());
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.delete(1));
        verify(ruleRepository).deleteById(1);
        verify(ruleRepository).findById(1);
    }

    /**
     * Method under test: {@link RuleServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete3() {
        // Arrange
        Optional<Rule> emptyResult = Optional.empty();
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> ruleServiceImpl.delete(1));
        verify(ruleRepository).findById(1);
    }

    /**
     * Method under test: {@link RuleServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);
        when(ruleRepository.findByIdWithAttributes(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        RuleDto actualFindByIdResult = ruleServiceImpl.findById(1);

        // Assert
        verify(ruleRepository).findByIdWithAttributes(1);
        LocalTime expectedToLocalTimeResult = actualFindByIdResult.getLastModified().toLocalTime();
        assertSame(expectedToLocalTimeResult, actualFindByIdResult.getCreateDate().toLocalTime());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById2() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category3);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule);
        ruleAttribute.setValue(10.0d);

        ArrayList<RuleAttribute> ruleAttributeList = new ArrayList<>();
        ruleAttributeList.add(ruleAttribute);
        Rule rule2 = mock(Rule.class);
        when(rule2.isEnabled()).thenReturn(true);
        when(rule2.getCategory()).thenReturn(category2);
        when(rule2.getCreatedBy()).thenReturn(1);
        when(rule2.getId()).thenReturn(1);
        when(rule2.getLastModifiedBy()).thenReturn(1);
        when(rule2.getDescription()).thenReturn("The characteristics of someone or something");
        when(rule2.getName()).thenReturn("Name");
        when(rule2.getCreateDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule2.getLastModified()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule2.getRuleAttributes()).thenReturn(ruleAttributeList);
        doNothing().when(rule2).setCategory(Mockito.<Category>any());
        doNothing().when(rule2).setCreateDate(Mockito.<LocalDateTime>any());
        doNothing().when(rule2).setCreatedBy(Mockito.<Integer>any());
        doNothing().when(rule2).setDescription(Mockito.<String>any());
        doNothing().when(rule2).setEnabled(anyBoolean());
        doNothing().when(rule2).setId(Mockito.<Integer>any());
        doNothing().when(rule2).setLastModified(Mockito.<LocalDateTime>any());
        doNothing().when(rule2).setLastModifiedBy(Mockito.<Integer>any());
        doNothing().when(rule2).setName(Mockito.<String>any());
        doNothing().when(rule2).setRuleAttributes(Mockito.<List<RuleAttribute>>any());
        doNothing().when(rule2).setRuleModifications(Mockito.<List<RuleModification>>any());
        rule2.setCategory(category);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule2);
        when(ruleRepository.findByIdWithAttributes(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        RuleDto actualFindByIdResult = ruleServiceImpl.findById(1);

        // Assert
        verify(rule2).getCategory();
        verify(rule2).getCreateDate();
        verify(rule2).getCreatedBy();
        verify(rule2).getDescription();
        verify(rule2).getId();
        verify(rule2).getLastModified();
        verify(rule2).getLastModifiedBy();
        verify(rule2).getName();
        verify(rule2).getRuleAttributes();
        verify(rule2).isEnabled();
        verify(rule2).setCategory(isA(Category.class));
        verify(rule2).setCreateDate(isA(LocalDateTime.class));
        verify(rule2).setCreatedBy(1);
        verify(rule2).setDescription("The characteristics of someone or something");
        verify(rule2).setEnabled(true);
        verify(rule2).setId(1);
        verify(rule2).setLastModified(isA(LocalDateTime.class));
        verify(rule2).setLastModifiedBy(1);
        verify(rule2).setName("Name");
        verify(rule2).setRuleAttributes(isA(List.class));
        verify(rule2).setRuleModifications(isA(List.class));
        verify(ruleRepository).findByIdWithAttributes(1);
        LocalTime expectedToLocalTimeResult = actualFindByIdResult.getLastModified().toLocalTime();
        assertSame(expectedToLocalTimeResult, actualFindByIdResult.getCreateDate().toLocalTime());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findById(Integer)}
     */

    @Test
    void testFindById4() {
        // Arrange
        Optional<Rule> emptyResult = Optional.empty();
        when(ruleRepository.findByIdWithAttributes(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act
        RuleDto actualFindByIdResult = ruleServiceImpl.findById(1);

        // Assert
        verify(ruleRepository).findByIdWithAttributes(1);
        assertNull(actualFindByIdResult);
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll() {
        // Arrange
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        Page<RuleDto> actualFindAllResult = ruleServiceImpl.findAll(1, 3);

        // Assert
        verify(ruleRepository).findAll(isA(Pageable.class));
        assertTrue(actualFindAllResult.toList().isEmpty());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll2() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<RuleDto> actualFindAllResult = ruleServiceImpl.findAll(1, 3);

        // Assert
        verify(ruleRepository).findAll(isA(Pageable.class));
        assertEquals(1, actualFindAllResult.toList().size());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll3() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("com.talan.adminmodule.entity.Category");
        category2.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category2);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("Description");
        rule2.setEnabled(false);
        rule2.setId(2);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("com.talan.adminmodule.entity.Rule");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule2);
        content.add(rule);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<RuleDto> actualFindAllResult = ruleServiceImpl.findAll(1, 3);

        // Assert
        verify(ruleRepository).findAll(isA(Pageable.class));
        assertEquals(2, actualFindAllResult.toList().size());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll4() {
        // Arrange
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenThrow(new InvalidEntityException("An error occurred"));

        // Act and Assert
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.findAll(1, 3));
        verify(ruleRepository).findAll(isA(Pageable.class));
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll5() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category3);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule);
        ruleAttribute.setValue(10.0d);

        ArrayList<RuleAttribute> ruleAttributeList = new ArrayList<>();
        ruleAttributeList.add(ruleAttribute);
        Rule rule2 = mock(Rule.class);
        when(rule2.isEnabled()).thenReturn(true);
        when(rule2.getCategory()).thenReturn(category2);
        when(rule2.getCreatedBy()).thenReturn(1);
        when(rule2.getId()).thenReturn(1);
        when(rule2.getLastModifiedBy()).thenReturn(1);
        when(rule2.getDescription()).thenReturn("The characteristics of someone or something");
        when(rule2.getName()).thenReturn("Name");
        when(rule2.getCreateDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule2.getLastModified()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule2.getRuleAttributes()).thenReturn(ruleAttributeList);
        doNothing().when(rule2).setCategory(Mockito.<Category>any());
        doNothing().when(rule2).setCreateDate(Mockito.<LocalDateTime>any());
        doNothing().when(rule2).setCreatedBy(Mockito.<Integer>any());
        doNothing().when(rule2).setDescription(Mockito.<String>any());
        doNothing().when(rule2).setEnabled(anyBoolean());
        doNothing().when(rule2).setId(Mockito.<Integer>any());
        doNothing().when(rule2).setLastModified(Mockito.<LocalDateTime>any());
        doNothing().when(rule2).setLastModifiedBy(Mockito.<Integer>any());
        doNothing().when(rule2).setName(Mockito.<String>any());
        doNothing().when(rule2).setRuleAttributes(Mockito.<List<RuleAttribute>>any());
        doNothing().when(rule2).setRuleModifications(Mockito.<List<RuleModification>>any());
        rule2.setCategory(category);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule2);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<RuleDto> actualFindAllResult = ruleServiceImpl.findAll(1, 3);

        // Assert
        verify(rule2).getCategory();
        verify(rule2).getCreateDate();
        verify(rule2).getCreatedBy();
        verify(rule2).getDescription();
        verify(rule2).getId();
        verify(rule2).getLastModified();
        verify(rule2).getLastModifiedBy();
        verify(rule2).getName();
        verify(rule2).getRuleAttributes();
        verify(rule2).isEnabled();
        verify(rule2).setCategory(isA(Category.class));
        verify(rule2).setCreateDate(isA(LocalDateTime.class));
        verify(rule2).setCreatedBy(1);
        verify(rule2).setDescription("The characteristics of someone or something");
        verify(rule2).setEnabled(true);
        verify(rule2).setId(1);
        verify(rule2).setLastModified(isA(LocalDateTime.class));
        verify(rule2).setLastModifiedBy(1);
        verify(rule2).setName("Name");
        verify(rule2).setRuleAttributes(isA(List.class));
        verify(rule2).setRuleModifications(isA(List.class));
        verify(ruleRepository).findAll(isA(Pageable.class));
        assertEquals(1, actualFindAllResult.toList().size());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll6() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category3);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule);
        ruleAttribute.setValue(10.0d);

        Attribute attribute2 = new Attribute();
        attribute2.setId(2);
        attribute2.setName("com.talan.adminmodule.entity.Attribute");
        attribute2.setRuleAttributes(new ArrayList<>());

        Category category4 = new Category();
        category4.setId(2);
        category4.setName("com.talan.adminmodule.entity.Category");
        category4.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category4);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(0);
        rule2.setDescription("Description");
        rule2.setEnabled(false);
        rule2.setId(2);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(0);
        rule2.setName("com.talan.adminmodule.entity.Rule");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute2 = new RuleAttribute();
        ruleAttribute2.setAttribute(attribute2);
        ruleAttribute2.setId(2L);
        ruleAttribute2.setPercentage(0.5d);
        ruleAttribute2.setRule(rule2);
        ruleAttribute2.setValue(0.5d);

        ArrayList<RuleAttribute> ruleAttributeList = new ArrayList<>();
        ruleAttributeList.add(ruleAttribute2);
        ruleAttributeList.add(ruleAttribute);
        Rule rule3 = mock(Rule.class);
        when(rule3.isEnabled()).thenReturn(true);
        when(rule3.getCategory()).thenReturn(category2);
        when(rule3.getCreatedBy()).thenReturn(1);
        when(rule3.getId()).thenReturn(1);
        when(rule3.getLastModifiedBy()).thenReturn(1);
        when(rule3.getDescription()).thenReturn("The characteristics of someone or something");
        when(rule3.getName()).thenReturn("Name");
        when(rule3.getCreateDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule3.getLastModified()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule3.getRuleAttributes()).thenReturn(ruleAttributeList);
        doNothing().when(rule3).setCategory(Mockito.<Category>any());
        doNothing().when(rule3).setCreateDate(Mockito.<LocalDateTime>any());
        doNothing().when(rule3).setCreatedBy(Mockito.<Integer>any());
        doNothing().when(rule3).setDescription(Mockito.<String>any());
        doNothing().when(rule3).setEnabled(anyBoolean());
        doNothing().when(rule3).setId(Mockito.<Integer>any());
        doNothing().when(rule3).setLastModified(Mockito.<LocalDateTime>any());
        doNothing().when(rule3).setLastModifiedBy(Mockito.<Integer>any());
        doNothing().when(rule3).setName(Mockito.<String>any());
        doNothing().when(rule3).setRuleAttributes(Mockito.<List<RuleAttribute>>any());
        doNothing().when(rule3).setRuleModifications(Mockito.<List<RuleModification>>any());
        rule3.setCategory(category);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("The characteristics of someone or something");
        rule3.setEnabled(true);
        rule3.setId(1);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("Name");
        rule3.setRuleAttributes(new ArrayList<>());
        rule3.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule3);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<RuleDto> actualFindAllResult = ruleServiceImpl.findAll(1, 3);

        // Assert
        verify(rule3).getCategory();
        verify(rule3).getCreateDate();
        verify(rule3).getCreatedBy();
        verify(rule3).getDescription();
        verify(rule3).getId();
        verify(rule3).getLastModified();
        verify(rule3).getLastModifiedBy();
        verify(rule3).getName();
        verify(rule3).getRuleAttributes();
        verify(rule3).isEnabled();
        verify(rule3).setCategory(isA(Category.class));
        verify(rule3).setCreateDate(isA(LocalDateTime.class));
        verify(rule3).setCreatedBy(1);
        verify(rule3).setDescription("The characteristics of someone or something");
        verify(rule3).setEnabled(true);
        verify(rule3).setId(1);
        verify(rule3).setLastModified(isA(LocalDateTime.class));
        verify(rule3).setLastModifiedBy(1);
        verify(rule3).setName("Name");
        verify(rule3).setRuleAttributes(isA(List.class));
        verify(rule3).setRuleModifications(isA(List.class));
        verify(ruleRepository).findAll(isA(Pageable.class));
        assertEquals(1, actualFindAllResult.toList().size());
    }

    /**
     * Method under test: {@link RuleServiceImpl#getModificationsByRuleId(Integer)}
     */
    @Test
    void testGetModificationsByRuleId() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        when(ruleModificationRepository.findByRuleOrderByModificationDateDesc(Mockito.<Rule>any()))
                .thenReturn(new ArrayList<>());

        // Act
        List<RuleModificationDto> actualModificationsByRuleId = ruleServiceImpl.getModificationsByRuleId(1);

        // Assert
        verify(ruleModificationRepository).findByRuleOrderByModificationDateDesc(isA(Rule.class));
        verify(ruleRepository).findById(1);
        assertTrue(actualModificationsByRuleId.isEmpty());
    }

    /**
     * Method under test: {@link RuleServiceImpl#getModificationsByRuleId(Integer)}
     */
    @Test
    void testGetModificationsByRuleId2() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        when(ruleModificationRepository.findByRuleOrderByModificationDateDesc(Mockito.<Rule>any()))
                .thenThrow(new InvalidEntityException("An error occurred"));

        // Act and Assert
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.getModificationsByRuleId(1));
        verify(ruleModificationRepository).findByRuleOrderByModificationDateDesc(isA(Rule.class));
        verify(ruleRepository).findById(1);
    }

    /**
     * Method under test: {@link RuleServiceImpl#getModificationsByRuleId(Integer)}
     */
    @Test
    void testGetModificationsByRuleId3() {
        // Arrange
        Optional<Rule> emptyResult = Optional.empty();
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> ruleServiceImpl.getModificationsByRuleId(1));
        verify(ruleRepository).findById(1);
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules() {
        // Arrange
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        Page<RuleDto> actualSearchRulesResult = ruleServiceImpl.searchRules(1, 3, "Query");

        // Assert
        verify(ruleRepository).search(eq("Query"), isA(Pageable.class));
        assertTrue(actualSearchRulesResult.toList().isEmpty());
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules2() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<RuleDto> actualSearchRulesResult = ruleServiceImpl.searchRules(1, 3, "Query");

        // Assert
        verify(ruleRepository).search(eq("Query"), isA(Pageable.class));
        assertEquals(1, actualSearchRulesResult.toList().size());
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules3() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("com.talan.adminmodule.entity.Category");
        category2.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category2);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("Description");
        rule2.setEnabled(false);
        rule2.setId(2);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("com.talan.adminmodule.entity.Rule");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule2);
        content.add(rule);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<RuleDto> actualSearchRulesResult = ruleServiceImpl.searchRules(1, 3, "Query");

        // Assert
        verify(ruleRepository).search(eq("Query"), isA(Pageable.class));
        assertEquals(2, actualSearchRulesResult.toList().size());
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules4() {
        // Arrange
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any()))
                .thenThrow(new InvalidEntityException("An error occurred"));

        // Act and Assert
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.searchRules(1, 3, "Query"));
        verify(ruleRepository).search(eq("Query"), isA(Pageable.class));
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules5() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category3);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule);
        ruleAttribute.setValue(10.0d);

        ArrayList<RuleAttribute> ruleAttributeList = new ArrayList<>();
        ruleAttributeList.add(ruleAttribute);
        Rule rule2 = mock(Rule.class);
        when(rule2.isEnabled()).thenReturn(true);
        when(rule2.getCategory()).thenReturn(category2);
        when(rule2.getCreatedBy()).thenReturn(1);
        when(rule2.getId()).thenReturn(1);
        when(rule2.getLastModifiedBy()).thenReturn(1);
        when(rule2.getDescription()).thenReturn("The characteristics of someone or something");
        when(rule2.getName()).thenReturn("Name");
        when(rule2.getCreateDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule2.getLastModified()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule2.getRuleAttributes()).thenReturn(ruleAttributeList);
        doNothing().when(rule2).setCategory(Mockito.<Category>any());
        doNothing().when(rule2).setCreateDate(Mockito.<LocalDateTime>any());
        doNothing().when(rule2).setCreatedBy(Mockito.<Integer>any());
        doNothing().when(rule2).setDescription(Mockito.<String>any());
        doNothing().when(rule2).setEnabled(anyBoolean());
        doNothing().when(rule2).setId(Mockito.<Integer>any());
        doNothing().when(rule2).setLastModified(Mockito.<LocalDateTime>any());
        doNothing().when(rule2).setLastModifiedBy(Mockito.<Integer>any());
        doNothing().when(rule2).setName(Mockito.<String>any());
        doNothing().when(rule2).setRuleAttributes(Mockito.<List<RuleAttribute>>any());
        doNothing().when(rule2).setRuleModifications(Mockito.<List<RuleModification>>any());
        rule2.setCategory(category);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule2);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<RuleDto> actualSearchRulesResult = ruleServiceImpl.searchRules(1, 3, "Query");

        // Assert
        verify(rule2).getCategory();
        verify(rule2).getCreateDate();
        verify(rule2).getCreatedBy();
        verify(rule2).getDescription();
        verify(rule2).getId();
        verify(rule2).getLastModified();
        verify(rule2).getLastModifiedBy();
        verify(rule2).getName();
        verify(rule2).getRuleAttributes();
        verify(rule2).isEnabled();
        verify(rule2).setCategory(isA(Category.class));
        verify(rule2).setCreateDate(isA(LocalDateTime.class));
        verify(rule2).setCreatedBy(1);
        verify(rule2).setDescription("The characteristics of someone or something");
        verify(rule2).setEnabled(true);
        verify(rule2).setId(1);
        verify(rule2).setLastModified(isA(LocalDateTime.class));
        verify(rule2).setLastModifiedBy(1);
        verify(rule2).setName("Name");
        verify(rule2).setRuleAttributes(isA(List.class));
        verify(rule2).setRuleModifications(isA(List.class));
        verify(ruleRepository).search(eq("Query"), isA(Pageable.class));
        assertEquals(1, actualSearchRulesResult.toList().size());
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules6() {
        // Arrange
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category3);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(1);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(1);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule);
        ruleAttribute.setValue(10.0d);

        Attribute attribute2 = new Attribute();
        attribute2.setId(2);
        attribute2.setName("com.talan.adminmodule.entity.Attribute");
        attribute2.setRuleAttributes(new ArrayList<>());

        Category category4 = new Category();
        category4.setId(2);
        category4.setName("com.talan.adminmodule.entity.Category");
        category4.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category4);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(0);
        rule2.setDescription("Description");
        rule2.setEnabled(false);
        rule2.setId(2);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(0);
        rule2.setName("com.talan.adminmodule.entity.Rule");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute2 = new RuleAttribute();
        ruleAttribute2.setAttribute(attribute2);
        ruleAttribute2.setId(2L);
        ruleAttribute2.setPercentage(0.5d);
        ruleAttribute2.setRule(rule2);
        ruleAttribute2.setValue(0.5d);

        ArrayList<RuleAttribute> ruleAttributeList = new ArrayList<>();
        ruleAttributeList.add(ruleAttribute2);
        ruleAttributeList.add(ruleAttribute);
        Rule rule3 = mock(Rule.class);
        when(rule3.isEnabled()).thenReturn(true);
        when(rule3.getCategory()).thenReturn(category2);
        when(rule3.getCreatedBy()).thenReturn(1);
        when(rule3.getId()).thenReturn(1);
        when(rule3.getLastModifiedBy()).thenReturn(1);
        when(rule3.getDescription()).thenReturn("The characteristics of someone or something");
        when(rule3.getName()).thenReturn("Name");
        when(rule3.getCreateDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule3.getLastModified()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(rule3.getRuleAttributes()).thenReturn(ruleAttributeList);
        doNothing().when(rule3).setCategory(Mockito.<Category>any());
        doNothing().when(rule3).setCreateDate(Mockito.<LocalDateTime>any());
        doNothing().when(rule3).setCreatedBy(Mockito.<Integer>any());
        doNothing().when(rule3).setDescription(Mockito.<String>any());
        doNothing().when(rule3).setEnabled(anyBoolean());
        doNothing().when(rule3).setId(Mockito.<Integer>any());
        doNothing().when(rule3).setLastModified(Mockito.<LocalDateTime>any());
        doNothing().when(rule3).setLastModifiedBy(Mockito.<Integer>any());
        doNothing().when(rule3).setName(Mockito.<String>any());
        doNothing().when(rule3).setRuleAttributes(Mockito.<List<RuleAttribute>>any());
        doNothing().when(rule3).setRuleModifications(Mockito.<List<RuleModification>>any());
        rule3.setCategory(category);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("The characteristics of someone or something");
        rule3.setEnabled(true);
        rule3.setId(1);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("Name");
        rule3.setRuleAttributes(new ArrayList<>());
        rule3.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule3);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        Page<RuleDto> actualSearchRulesResult = ruleServiceImpl.searchRules(1, 3, "Query");

        // Assert
        verify(rule3).getCategory();
        verify(rule3).getCreateDate();
        verify(rule3).getCreatedBy();
        verify(rule3).getDescription();
        verify(rule3).getId();
        verify(rule3).getLastModified();
        verify(rule3).getLastModifiedBy();
        verify(rule3).getName();
        verify(rule3).getRuleAttributes();
        verify(rule3).isEnabled();
        verify(rule3).setCategory(isA(Category.class));
        verify(rule3).setCreateDate(isA(LocalDateTime.class));
        verify(rule3).setCreatedBy(1);
        verify(rule3).setDescription("The characteristics of someone or something");
        verify(rule3).setEnabled(true);
        verify(rule3).setId(1);
        verify(rule3).setLastModified(isA(LocalDateTime.class));
        verify(rule3).setLastModifiedBy(1);
        verify(rule3).setName("Name");
        verify(rule3).setRuleAttributes(isA(List.class));
        verify(rule3).setRuleModifications(isA(List.class));
        verify(ruleRepository).search(eq("Query"), isA(Pageable.class));
        assertEquals(1, actualSearchRulesResult.toList().size());
    }
}
