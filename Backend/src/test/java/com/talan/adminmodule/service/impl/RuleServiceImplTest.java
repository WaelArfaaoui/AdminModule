package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.talan.adminmodule.entity.Attribute;
import com.talan.adminmodule.entity.Category;
import com.talan.adminmodule.entity.Rule;
import com.talan.adminmodule.entity.RuleAttribute;
import com.talan.adminmodule.entity.RuleModification;
import com.talan.adminmodule.exception.EntityNotFoundException;
import com.talan.adminmodule.exception.InvalidEntityException;
import com.talan.adminmodule.repository.AttributeRepository;
import com.talan.adminmodule.repository.CategoryRepository;
import com.talan.adminmodule.repository.RuleAttributeRepository;
import com.talan.adminmodule.repository.RuleModificationRepository;
import com.talan.adminmodule.repository.RuleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
     * Method under test: {@link RuleServiceImpl#save(RuleDto)}
     */
    @Test
    void testSave() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        ArrayList<Rule> rules = new ArrayList<>();
        category.setRules(rules);
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
        RuleDto ruleDto = mock(RuleDto.class);
        when(ruleDto.getCategory()).thenReturn(CategoryDto.builder().id(1).name("Name").build());
        when(ruleDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(ruleDto.getName()).thenReturn("Name");
        when(ruleDto.getAttributeDtos()).thenReturn(new ArrayList<>());
        RuleDto actualSaveResult = ruleServiceImpl.save(ruleDto);
        assertEquals(rules, actualSaveResult.getAttributeDtos());
        assertTrue(actualSaveResult.isEnabled());
        assertEquals("Name", actualSaveResult.getName());
        assertEquals(1, actualSaveResult.getLastModifiedBy().intValue());
        assertEquals(1, actualSaveResult.getId().intValue());
        assertEquals("00:00", actualSaveResult.getLastModified().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualSaveResult.getDescription());
        assertEquals(1, actualSaveResult.getCreatedBy().intValue());
        assertEquals("00:00", actualSaveResult.getCreateDate().toLocalTime().toString());
        CategoryDto category5 = actualSaveResult.getCategory();
        assertEquals(1, category5.getId().intValue());
        assertEquals("Name", category5.getName());
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(ruleModificationRepository).save(Mockito.<RuleModification>any());
        verify(ruleDto, atLeast(1)).getCategory();
        verify(ruleDto, atLeast(1)).getDescription();
        verify(ruleDto, atLeast(1)).getName();
        verify(ruleDto, atLeast(1)).getAttributeDtos();
    }

    /**
     * Method under test: {@link RuleServiceImpl#save(RuleDto)}
     */
    @Test
    void testSave2() {
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
        RuleDto ruleDto = mock(RuleDto.class);
        when(ruleDto.getCategory()).thenReturn(CategoryDto.builder().id(1).name("Name").build());
        when(ruleDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(ruleDto.getName()).thenReturn("Name");
        when(ruleDto.getAttributeDtos()).thenReturn(new ArrayList<>());
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.save(ruleDto));
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(ruleModificationRepository).save(Mockito.<RuleModification>any());
        verify(ruleDto, atLeast(1)).getCategory();
        verify(ruleDto, atLeast(1)).getDescription();
        verify(ruleDto, atLeast(1)).getName();
        verify(ruleDto).getAttributeDtos();
    }

    /**
     * Method under test: {@link RuleServiceImpl#save(RuleDto)}
     */
    @Test
    void testSave3() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        ArrayList<Rule> rules = new ArrayList<>();
        category.setRules(rules);

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
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult
                .category(CategoryDto.builder().id(1).name("Name").build());
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto actualSaveResult = ruleServiceImpl
                .save(idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay()).lastModifiedBy(1).name("Name").build());
        assertEquals(rules, actualSaveResult.getAttributeDtos());
        assertTrue(actualSaveResult.isEnabled());
        assertEquals("Name", actualSaveResult.getName());
        assertEquals(1, actualSaveResult.getLastModifiedBy().intValue());
        assertEquals(1, actualSaveResult.getId().intValue());
        assertEquals("00:00", actualSaveResult.getLastModified().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualSaveResult.getDescription());
        assertEquals(1, actualSaveResult.getCreatedBy().intValue());
        assertEquals("00:00", actualSaveResult.getCreateDate().toLocalTime().toString());
        CategoryDto category5 = actualSaveResult.getCategory();
        assertEquals(1, category5.getId().intValue());
        assertEquals("Name", category5.getName());
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(ruleModificationRepository).save(Mockito.<RuleModification>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateStatus(Integer, boolean)}
     */
    @Test
    void testUpdateStatus() {
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
        ArrayList<Rule> rules = new ArrayList<>();
        category2.setRules(rules);

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
        RuleDto actualUpdateStatusResult = ruleServiceImpl.updateStatus(1, true);
        assertEquals(rules, actualUpdateStatusResult.getAttributeDtos());
        assertTrue(actualUpdateStatusResult.isEnabled());
        assertEquals("Name", actualUpdateStatusResult.getName());
        assertEquals(1, actualUpdateStatusResult.getLastModifiedBy().intValue());
        assertEquals(1, actualUpdateStatusResult.getId().intValue());
        assertEquals("00:00", actualUpdateStatusResult.getLastModified().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualUpdateStatusResult.getDescription());
        assertEquals(1, actualUpdateStatusResult.getCreatedBy().intValue());
        assertEquals("00:00", actualUpdateStatusResult.getCreateDate().toLocalTime().toString());
        CategoryDto category3 = actualUpdateStatusResult.getCategory();
        assertEquals(1, category3.getId().intValue());
        assertEquals("Name", category3.getName());
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(ruleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateStatus(Integer, boolean)}
     */
    @Test
    void testUpdateStatus2() {
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
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.updateStatus(1, true));
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(ruleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateStatus(Integer, boolean)}
     */
    @Test
    void testUpdateStatus3() {
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

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category3);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(3);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(3);
        rule2.setName("Name");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule2);
        ruleAttribute.setValue(10.0d);

        ArrayList<RuleAttribute> ruleAttributes = new ArrayList<>();
        ruleAttributes.add(ruleAttribute);

        Rule rule3 = new Rule();
        rule3.setCategory(category2);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("The characteristics of someone or something");
        rule3.setEnabled(true);
        rule3.setId(1);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("Name");
        rule3.setRuleAttributes(ruleAttributes);
        rule3.setRuleModifications(new ArrayList<>());
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule3);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        RuleDto actualUpdateStatusResult = ruleServiceImpl.updateStatus(1, true);
        List<AttributeDataDto> attributeDtos = actualUpdateStatusResult.getAttributeDtos();
        assertEquals(1, attributeDtos.size());
        assertTrue(actualUpdateStatusResult.isEnabled());
        assertEquals("Name", actualUpdateStatusResult.getName());
        assertEquals(1, actualUpdateStatusResult.getCreatedBy().intValue());
        assertEquals(1, actualUpdateStatusResult.getLastModifiedBy().intValue());
        assertEquals("The characteristics of someone or something", actualUpdateStatusResult.getDescription());
        assertEquals(1, actualUpdateStatusResult.getId().intValue());
        assertEquals("00:00", actualUpdateStatusResult.getLastModified().toLocalTime().toString());
        assertEquals("1970-01-01", actualUpdateStatusResult.getCreateDate().toLocalDate().toString());
        CategoryDto category4 = actualUpdateStatusResult.getCategory();
        assertEquals(1, category4.getId().intValue());
        assertEquals("Name", category4.getName());
        AttributeDataDto getResult = attributeDtos.get(0);
        assertEquals(10.0d, getResult.getPercentage().doubleValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(10.0d, getResult.getValue().doubleValue());
        AttributeDto name = getResult.getName();
        assertEquals(1, name.getId().intValue());
        assertEquals("Name", name.getName());
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(ruleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateStatus(Integer, boolean)}
     */
    @Test
    void testUpdateStatus4() {
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

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category3);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(3);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(3);
        rule2.setName("Name");
        rule2.setRuleAttributes(new ArrayList<>());
        rule2.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule2);
        ruleAttribute.setValue(10.0d);

        Attribute attribute2 = new Attribute();
        attribute2.setId(2);
        attribute2.setName("com.talan.adminmodule.entity.Attribute");
        attribute2.setRuleAttributes(new ArrayList<>());

        Category category4 = new Category();
        category4.setId(2);
        category4.setName("com.talan.adminmodule.entity.Category");
        category4.setRules(new ArrayList<>());

        Rule rule3 = new Rule();
        rule3.setCategory(category4);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("Description");
        rule3.setEnabled(false);
        rule3.setId(2);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("com.talan.adminmodule.entity.Rule");
        rule3.setRuleAttributes(new ArrayList<>());
        rule3.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute2 = new RuleAttribute();
        ruleAttribute2.setAttribute(attribute2);
        ruleAttribute2.setId(2L);
        ruleAttribute2.setPercentage(0.5d);
        ruleAttribute2.setRule(rule3);
        ruleAttribute2.setValue(0.5d);

        ArrayList<RuleAttribute> ruleAttributes = new ArrayList<>();
        ruleAttributes.add(ruleAttribute2);
        ruleAttributes.add(ruleAttribute);

        Rule rule4 = new Rule();
        rule4.setCategory(category2);
        rule4.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule4.setCreatedBy(1);
        rule4.setDescription("The characteristics of someone or something");
        rule4.setEnabled(true);
        rule4.setId(1);
        rule4.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule4.setLastModifiedBy(1);
        rule4.setName("Name");
        rule4.setRuleAttributes(ruleAttributes);
        rule4.setRuleModifications(new ArrayList<>());
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule4);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        RuleDto actualUpdateStatusResult = ruleServiceImpl.updateStatus(1, true);
        List<AttributeDataDto> attributeDtos = actualUpdateStatusResult.getAttributeDtos();
        assertEquals(2, attributeDtos.size());
        assertTrue(actualUpdateStatusResult.isEnabled());
        assertEquals(1, actualUpdateStatusResult.getCreatedBy().intValue());
        assertEquals(1, actualUpdateStatusResult.getLastModifiedBy().intValue());
        assertEquals("Name", actualUpdateStatusResult.getName());
        assertEquals("The characteristics of someone or something", actualUpdateStatusResult.getDescription());
        assertEquals("00:00", actualUpdateStatusResult.getCreateDate().toLocalTime().toString());
        assertEquals("1970-01-01", actualUpdateStatusResult.getLastModified().toLocalDate().toString());
        assertEquals(1, actualUpdateStatusResult.getId().intValue());
        CategoryDto category5 = actualUpdateStatusResult.getCategory();
        assertEquals(1, category5.getId().intValue());
        assertEquals("Name", category5.getName());
        AttributeDataDto getResult = attributeDtos.get(1);
        assertEquals(10.0d, getResult.getValue().doubleValue());
        AttributeDataDto getResult2 = attributeDtos.get(0);
        assertEquals(0.5d, getResult2.getValue().doubleValue());
        assertEquals(0.5d, getResult2.getPercentage().doubleValue());
        assertEquals(2, getResult2.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(10.0d, getResult.getPercentage().doubleValue());
        AttributeDto name = getResult2.getName();
        assertEquals("com.talan.adminmodule.entity.Attribute", name.getName());
        AttributeDto name2 = getResult.getName();
        assertEquals("Name", name2.getName());
        assertEquals(1, name2.getId().intValue());
        assertEquals(2, name.getId().intValue());
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(ruleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateStatus(Integer, boolean)}
     */
    @Test
    void testUpdateStatus5() {
        Optional<Rule> emptyResult = Optional.empty();
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        assertThrows(EntityNotFoundException.class, () -> ruleServiceImpl.updateStatus(1, true));
        verify(ruleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateRule(Integer, RuleDto, String, Integer)}
     */
    @Test
    void testUpdateRule() {
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
        ArrayList<Rule> rules = new ArrayList<>();
        category2.setRules(rules);

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
        RuleDto updatedRuleDto = mock(RuleDto.class);
        when(updatedRuleDto.getCategory()).thenReturn(CategoryDto.builder().id(1).name("Name").build());
        when(updatedRuleDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(updatedRuleDto.getName()).thenReturn("Name");
        when(updatedRuleDto.getAttributeDtos()).thenReturn(new ArrayList<>());
        RuleDto actualUpdateRuleResult = ruleServiceImpl.updateRule(1, updatedRuleDto, "Mod Description", 1);
        assertEquals(rules, actualUpdateRuleResult.getAttributeDtos());
        assertTrue(actualUpdateRuleResult.isEnabled());
        assertEquals("Name", actualUpdateRuleResult.getName());
        assertEquals(1, actualUpdateRuleResult.getLastModifiedBy().intValue());
        assertEquals(1, actualUpdateRuleResult.getId().intValue());
        assertEquals("00:00", actualUpdateRuleResult.getLastModified().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualUpdateRuleResult.getDescription());
        assertEquals(1, actualUpdateRuleResult.getCreatedBy().intValue());
        assertEquals("00:00", actualUpdateRuleResult.getCreateDate().toLocalTime().toString());
        CategoryDto category6 = actualUpdateRuleResult.getCategory();
        assertEquals(1, category6.getId().intValue());
        assertEquals("Name", category6.getName());
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(ruleRepository).findById(Mockito.<Integer>any());
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(ruleModificationRepository).save(Mockito.<RuleModification>any());
        verify(ruleAttributeRepository).deleteByRule(Mockito.<Rule>any());
        verify(updatedRuleDto, atLeast(1)).getCategory();
        verify(updatedRuleDto, atLeast(1)).getDescription();
        verify(updatedRuleDto, atLeast(1)).getName();
        verify(updatedRuleDto, atLeast(1)).getAttributeDtos();
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateRule(Integer, RuleDto, String, Integer)}
     */
    @Test
    void testUpdateRule2() {
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
        RuleDto updatedRuleDto = mock(RuleDto.class);
        when(updatedRuleDto.getCategory()).thenReturn(CategoryDto.builder().id(1).name("Name").build());
        when(updatedRuleDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(updatedRuleDto.getName()).thenReturn("Name");
        when(updatedRuleDto.getAttributeDtos()).thenReturn(new ArrayList<>());
        assertThrows(InvalidEntityException.class,
                () -> ruleServiceImpl.updateRule(1, updatedRuleDto, "Mod Description", 1));
        verify(ruleRepository).findById(Mockito.<Integer>any());
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(ruleAttributeRepository).deleteByRule(Mockito.<Rule>any());
        verify(updatedRuleDto, atLeast(1)).getCategory();
        verify(updatedRuleDto, atLeast(1)).getDescription();
        verify(updatedRuleDto, atLeast(1)).getName();
        verify(updatedRuleDto).getAttributeDtos();
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateRule(Integer, RuleDto, String, Integer)}
     */
    @Test
    void testUpdateRule3() {
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

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category3);
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

        ArrayList<RuleAttribute> ruleAttributes = new ArrayList<>();
        ruleAttributes.add(ruleAttribute);

        Rule rule3 = new Rule();
        rule3.setCategory(category2);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("The characteristics of someone or something");
        rule3.setEnabled(true);
        rule3.setId(1);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("Name");
        rule3.setRuleAttributes(ruleAttributes);
        rule3.setRuleModifications(new ArrayList<>());
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule3);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

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

        Rule rule4 = new Rule();
        rule4.setCategory(category6);
        rule4.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule4.setCreatedBy(1);
        rule4.setDescription("The characteristics of someone or something");
        rule4.setEnabled(true);
        rule4.setId(1);
        rule4.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule4.setLastModifiedBy(1);
        rule4.setName("Name");
        rule4.setRuleAttributes(new ArrayList<>());
        rule4.setRuleModifications(new ArrayList<>());

        RuleModification ruleModification = new RuleModification();
        ruleModification.setId(1);
        ruleModification.setModificationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ruleModification.setModificationDescription("Modification Description");
        ruleModification.setModifiedBy(1);
        ruleModification.setRule(rule4);
        ruleModification.setRuleName("Rule Name");
        when(ruleModificationRepository.save(Mockito.<RuleModification>any())).thenReturn(ruleModification);
        doNothing().when(ruleAttributeRepository).deleteByRule(Mockito.<Rule>any());
        RuleDto updatedRuleDto = mock(RuleDto.class);
        when(updatedRuleDto.getCategory()).thenReturn(CategoryDto.builder().id(1).name("Name").build());
        when(updatedRuleDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(updatedRuleDto.getName()).thenReturn("Name");
        when(updatedRuleDto.getAttributeDtos()).thenReturn(new ArrayList<>());
        RuleDto actualUpdateRuleResult = ruleServiceImpl.updateRule(1, updatedRuleDto, "Mod Description", 1);
        List<AttributeDataDto> attributeDtos = actualUpdateRuleResult.getAttributeDtos();
        assertEquals(1, attributeDtos.size());
        assertTrue(actualUpdateRuleResult.isEnabled());
        assertEquals("Name", actualUpdateRuleResult.getName());
        assertEquals(1, actualUpdateRuleResult.getCreatedBy().intValue());
        assertEquals(1, actualUpdateRuleResult.getLastModifiedBy().intValue());
        assertEquals("The characteristics of someone or something", actualUpdateRuleResult.getDescription());
        assertEquals(1, actualUpdateRuleResult.getId().intValue());
        assertEquals("00:00", actualUpdateRuleResult.getLastModified().toLocalTime().toString());
        assertEquals("1970-01-01", actualUpdateRuleResult.getCreateDate().toLocalDate().toString());
        CategoryDto category7 = actualUpdateRuleResult.getCategory();
        assertEquals(1, category7.getId().intValue());
        assertEquals("Name", category7.getName());
        AttributeDataDto getResult = attributeDtos.get(0);
        assertEquals(10.0d, getResult.getPercentage().doubleValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(10.0d, getResult.getValue().doubleValue());
        AttributeDto name = getResult.getName();
        assertEquals(1, name.getId().intValue());
        assertEquals("Name", name.getName());
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(ruleRepository).findById(Mockito.<Integer>any());
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(ruleModificationRepository).save(Mockito.<RuleModification>any());
        verify(ruleAttributeRepository).deleteByRule(Mockito.<Rule>any());
        verify(updatedRuleDto, atLeast(1)).getCategory();
        verify(updatedRuleDto, atLeast(1)).getDescription();
        verify(updatedRuleDto, atLeast(1)).getName();
        verify(updatedRuleDto, atLeast(1)).getAttributeDtos();
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateRule(Integer, RuleDto, String, Integer)}
     */
    @Test
    void testUpdateRule4() {
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

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category3);
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

        Attribute attribute2 = new Attribute();
        attribute2.setId(2);
        attribute2.setName("com.talan.adminmodule.entity.Attribute");
        attribute2.setRuleAttributes(new ArrayList<>());

        Category category4 = new Category();
        category4.setId(2);
        category4.setName("com.talan.adminmodule.entity.Category");
        category4.setRules(new ArrayList<>());

        Rule rule3 = new Rule();
        rule3.setCategory(category4);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(3);
        rule3.setDescription("Description");
        rule3.setEnabled(false);
        rule3.setId(2);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(3);
        rule3.setName("com.talan.adminmodule.entity.Rule");
        rule3.setRuleAttributes(new ArrayList<>());
        rule3.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute2 = new RuleAttribute();
        ruleAttribute2.setAttribute(attribute2);
        ruleAttribute2.setId(2L);
        ruleAttribute2.setPercentage(0.5d);
        ruleAttribute2.setRule(rule3);
        ruleAttribute2.setValue(0.5d);

        ArrayList<RuleAttribute> ruleAttributes = new ArrayList<>();
        ruleAttributes.add(ruleAttribute2);
        ruleAttributes.add(ruleAttribute);

        Rule rule4 = new Rule();
        rule4.setCategory(category2);
        rule4.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule4.setCreatedBy(1);
        rule4.setDescription("The characteristics of someone or something");
        rule4.setEnabled(true);
        rule4.setId(1);
        rule4.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule4.setLastModifiedBy(1);
        rule4.setName("Name");
        rule4.setRuleAttributes(ruleAttributes);
        rule4.setRuleModifications(new ArrayList<>());
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule4);
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Category category5 = new Category();
        category5.setId(1);
        category5.setName("Name");
        category5.setRules(new ArrayList<>());

        Category category6 = new Category();
        category6.setId(1);
        category6.setName("Name");
        category6.setRules(new ArrayList<>());
        when(categoryRepository.findByName(Mockito.<String>any())).thenReturn(category5);
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category6);

        Category category7 = new Category();
        category7.setId(1);
        category7.setName("Name");
        category7.setRules(new ArrayList<>());

        Rule rule5 = new Rule();
        rule5.setCategory(category7);
        rule5.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule5.setCreatedBy(1);
        rule5.setDescription("The characteristics of someone or something");
        rule5.setEnabled(true);
        rule5.setId(1);
        rule5.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule5.setLastModifiedBy(1);
        rule5.setName("Name");
        rule5.setRuleAttributes(new ArrayList<>());
        rule5.setRuleModifications(new ArrayList<>());

        RuleModification ruleModification = new RuleModification();
        ruleModification.setId(1);
        ruleModification.setModificationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ruleModification.setModificationDescription("Modification Description");
        ruleModification.setModifiedBy(1);
        ruleModification.setRule(rule5);
        ruleModification.setRuleName("Rule Name");
        when(ruleModificationRepository.save(Mockito.<RuleModification>any())).thenReturn(ruleModification);
        doNothing().when(ruleAttributeRepository).deleteByRule(Mockito.<Rule>any());
        RuleDto updatedRuleDto = mock(RuleDto.class);
        when(updatedRuleDto.getCategory()).thenReturn(CategoryDto.builder().id(1).name("Name").build());
        when(updatedRuleDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(updatedRuleDto.getName()).thenReturn("Name");
        when(updatedRuleDto.getAttributeDtos()).thenReturn(new ArrayList<>());
        RuleDto actualUpdateRuleResult = ruleServiceImpl.updateRule(1, updatedRuleDto, "Mod Description", 1);
        List<AttributeDataDto> attributeDtos = actualUpdateRuleResult.getAttributeDtos();
        assertEquals(2, attributeDtos.size());
        assertTrue(actualUpdateRuleResult.isEnabled());
        assertEquals(1, actualUpdateRuleResult.getCreatedBy().intValue());
        assertEquals(1, actualUpdateRuleResult.getLastModifiedBy().intValue());
        assertEquals("Name", actualUpdateRuleResult.getName());
        assertEquals("The characteristics of someone or something", actualUpdateRuleResult.getDescription());
        assertEquals("00:00", actualUpdateRuleResult.getCreateDate().toLocalTime().toString());
        assertEquals("1970-01-01", actualUpdateRuleResult.getLastModified().toLocalDate().toString());
        assertEquals(1, actualUpdateRuleResult.getId().intValue());
        CategoryDto category8 = actualUpdateRuleResult.getCategory();
        assertEquals(1, category8.getId().intValue());
        assertEquals("Name", category8.getName());
        AttributeDataDto getResult = attributeDtos.get(1);
        assertEquals(10.0d, getResult.getValue().doubleValue());
        AttributeDataDto getResult2 = attributeDtos.get(0);
        assertEquals(0.5d, getResult2.getValue().doubleValue());
        assertEquals(0.5d, getResult2.getPercentage().doubleValue());
        assertEquals(2, getResult2.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(10.0d, getResult.getPercentage().doubleValue());
        AttributeDto name = getResult2.getName();
        assertEquals("com.talan.adminmodule.entity.Attribute", name.getName());
        AttributeDto name2 = getResult.getName();
        assertEquals("Name", name2.getName());
        assertEquals(1, name2.getId().intValue());
        assertEquals(2, name.getId().intValue());
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(ruleRepository).findById(Mockito.<Integer>any());
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(ruleModificationRepository).save(Mockito.<RuleModification>any());
        verify(ruleAttributeRepository).deleteByRule(Mockito.<Rule>any());
        verify(updatedRuleDto, atLeast(1)).getCategory();
        verify(updatedRuleDto, atLeast(1)).getDescription();
        verify(updatedRuleDto, atLeast(1)).getName();
        verify(updatedRuleDto, atLeast(1)).getAttributeDtos();
    }

    /**
     * Method under test: {@link RuleServiceImpl#updateRule(Integer, RuleDto, String, Integer)}
     */
    @Test
    void testUpdateRule5() {
        Optional<Rule> emptyResult = Optional.empty();
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        assertThrows(EntityNotFoundException.class,
                () -> ruleServiceImpl.updateRule(1, mock(RuleDto.class), "Mod Description", 1));
        verify(ruleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#saveRuleModification(Rule, String)}
     */
    @Test
    void testSaveRuleModification() {
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
        ruleServiceImpl.saveRuleModification(existingRule, "Mod Description");
        verify(ruleModificationRepository).save(Mockito.<RuleModification>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#saveRuleModification(Rule, String)}
     */
    @Test
    void testSaveRuleModification2() {
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
        assertThrows(InvalidEntityException.class,
                () -> ruleServiceImpl.saveRuleModification(existingRule, "Mod Description"));
        verify(ruleModificationRepository).save(Mockito.<RuleModification>any());
    }

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

    /**
     * Method under test: {@link RuleServiceImpl#addRuleAttribute(Attribute, Rule, AttributeDataDto)}
     */
    @Test
    void testAddRuleAttribute2() {
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
        AttributeDataDto attributeDto = mock(AttributeDataDto.class);
        when(attributeDto.getPercentage()).thenReturn(10.0d);
        when(attributeDto.getValue()).thenReturn(10.0d);
        RuleAttribute actualAddRuleAttributeResult = ruleServiceImpl.addRuleAttribute(attribute, existingRule,
                attributeDto);
        assertSame(attribute, actualAddRuleAttributeResult.getAttribute());
        assertEquals(10.0d, actualAddRuleAttributeResult.getValue().doubleValue());
        assertSame(existingRule, actualAddRuleAttributeResult.getRule());
        assertEquals(10.0d, actualAddRuleAttributeResult.getPercentage().doubleValue());
        verify(attributeDto).getPercentage();
        verify(attributeDto).getValue();
    }

    /**
     * Method under test: {@link RuleServiceImpl#addRuleAttribute(Attribute, Rule, AttributeDataDto)}
     */
    @Test
    void testAddRuleAttribute3() {
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
        AttributeDataDto attributeDto = mock(AttributeDataDto.class);
        when(attributeDto.getPercentage()).thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class,
                () -> ruleServiceImpl.addRuleAttribute(attribute, existingRule, attributeDto));
        verify(attributeDto).getPercentage();
    }

    /**
     * Method under test: {@link RuleServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete() {
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
        ruleServiceImpl.delete(1);
        verify(ruleRepository).findById(Mockito.<Integer>any());
        verify(ruleRepository).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete2() {
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
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.delete(1));
        verify(ruleRepository).findById(Mockito.<Integer>any());
        verify(ruleRepository).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete3() {
        Optional<Rule> emptyResult = Optional.empty();
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        assertThrows(EntityNotFoundException.class, () -> ruleServiceImpl.delete(1));
        verify(ruleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        ArrayList<Rule> rules = new ArrayList<>();
        category.setRules(rules);

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
        RuleDto actualFindByIdResult = ruleServiceImpl.findById(1);
        assertEquals(rules, actualFindByIdResult.getAttributeDtos());
        assertTrue(actualFindByIdResult.isEnabled());
        assertEquals("Name", actualFindByIdResult.getName());
        assertEquals(1, actualFindByIdResult.getLastModifiedBy().intValue());
        assertEquals(1, actualFindByIdResult.getId().intValue());
        assertEquals("00:00", actualFindByIdResult.getLastModified().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualFindByIdResult.getDescription());
        assertEquals(1, actualFindByIdResult.getCreatedBy().intValue());
        assertEquals("00:00", actualFindByIdResult.getCreateDate().toLocalTime().toString());
        CategoryDto category2 = actualFindByIdResult.getCategory();
        assertEquals(1, category2.getId().intValue());
        assertEquals("Name", category2.getName());
        verify(ruleRepository).findByIdWithAttributes(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById2() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category2);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(3);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(3);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule);
        ruleAttribute.setValue(10.0d);

        ArrayList<RuleAttribute> ruleAttributes = new ArrayList<>();
        ruleAttributes.add(ruleAttribute);

        Rule rule2 = new Rule();
        rule2.setCategory(category);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(ruleAttributes);
        rule2.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule2);
        when(ruleRepository.findByIdWithAttributes(Mockito.<Integer>any())).thenReturn(ofResult);
        RuleDto actualFindByIdResult = ruleServiceImpl.findById(1);
        List<AttributeDataDto> attributeDtos = actualFindByIdResult.getAttributeDtos();
        assertEquals(1, attributeDtos.size());
        assertTrue(actualFindByIdResult.isEnabled());
        assertEquals("Name", actualFindByIdResult.getName());
        assertEquals(1, actualFindByIdResult.getCreatedBy().intValue());
        assertEquals(1, actualFindByIdResult.getLastModifiedBy().intValue());
        assertEquals("The characteristics of someone or something", actualFindByIdResult.getDescription());
        assertEquals(1, actualFindByIdResult.getId().intValue());
        assertEquals("00:00", actualFindByIdResult.getLastModified().toLocalTime().toString());
        assertEquals("1970-01-01", actualFindByIdResult.getCreateDate().toLocalDate().toString());
        CategoryDto category3 = actualFindByIdResult.getCategory();
        assertEquals(1, category3.getId().intValue());
        assertEquals("Name", category3.getName());
        AttributeDataDto getResult = attributeDtos.get(0);
        assertEquals(10.0d, getResult.getPercentage().doubleValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(10.0d, getResult.getValue().doubleValue());
        AttributeDto name = getResult.getName();
        assertEquals(1, name.getId().intValue());
        assertEquals("Name", name.getName());
        verify(ruleRepository).findByIdWithAttributes(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById3() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category2);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(3);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(3);
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

        Category category3 = new Category();
        category3.setId(2);
        category3.setName("com.talan.adminmodule.entity.Category");
        category3.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category3);
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

        RuleAttribute ruleAttribute2 = new RuleAttribute();
        ruleAttribute2.setAttribute(attribute2);
        ruleAttribute2.setId(2L);
        ruleAttribute2.setPercentage(0.5d);
        ruleAttribute2.setRule(rule2);
        ruleAttribute2.setValue(0.5d);

        ArrayList<RuleAttribute> ruleAttributes = new ArrayList<>();
        ruleAttributes.add(ruleAttribute2);
        ruleAttributes.add(ruleAttribute);

        Rule rule3 = new Rule();
        rule3.setCategory(category);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("The characteristics of someone or something");
        rule3.setEnabled(true);
        rule3.setId(1);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("Name");
        rule3.setRuleAttributes(ruleAttributes);
        rule3.setRuleModifications(new ArrayList<>());
        Optional<Rule> ofResult = Optional.of(rule3);
        when(ruleRepository.findByIdWithAttributes(Mockito.<Integer>any())).thenReturn(ofResult);
        RuleDto actualFindByIdResult = ruleServiceImpl.findById(1);
        List<AttributeDataDto> attributeDtos = actualFindByIdResult.getAttributeDtos();
        assertEquals(2, attributeDtos.size());
        assertTrue(actualFindByIdResult.isEnabled());
        assertEquals(1, actualFindByIdResult.getCreatedBy().intValue());
        assertEquals(1, actualFindByIdResult.getLastModifiedBy().intValue());
        assertEquals("Name", actualFindByIdResult.getName());
        assertEquals("The characteristics of someone or something", actualFindByIdResult.getDescription());
        assertEquals("00:00", actualFindByIdResult.getCreateDate().toLocalTime().toString());
        assertEquals("1970-01-01", actualFindByIdResult.getLastModified().toLocalDate().toString());
        assertEquals(1, actualFindByIdResult.getId().intValue());
        CategoryDto category4 = actualFindByIdResult.getCategory();
        assertEquals(1, category4.getId().intValue());
        assertEquals("Name", category4.getName());
        AttributeDataDto getResult = attributeDtos.get(1);
        assertEquals(10.0d, getResult.getValue().doubleValue());
        AttributeDataDto getResult2 = attributeDtos.get(0);
        assertEquals(0.5d, getResult2.getValue().doubleValue());
        assertEquals(0.5d, getResult2.getPercentage().doubleValue());
        assertEquals(2, getResult2.getId().intValue());
        assertEquals(1, getResult.getId().intValue());
        assertEquals(10.0d, getResult.getPercentage().doubleValue());
        AttributeDto name = getResult2.getName();
        assertEquals("com.talan.adminmodule.entity.Attribute", name.getName());
        AttributeDto name2 = getResult.getName();
        assertEquals("Name", name2.getName());
        assertEquals(1, name2.getId().intValue());
        assertEquals(2, name.getId().intValue());
        verify(ruleRepository).findByIdWithAttributes(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById4() {
        Optional<Rule> emptyResult = Optional.empty();
        when(ruleRepository.findByIdWithAttributes(Mockito.<Integer>any())).thenReturn(emptyResult);
        assertNull(ruleServiceImpl.findById(1));
        verify(ruleRepository).findByIdWithAttributes(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById5() {
        when(ruleRepository.findByIdWithAttributes(Mockito.<Integer>any()))
                .thenThrow(new InvalidEntityException("An error occurred"));
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.findById(1));
        verify(ruleRepository).findByIdWithAttributes(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll() {
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(ruleServiceImpl.findAll(1, 3).toList().isEmpty());
        verify(ruleRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll2() {
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
        assertEquals(1, ruleServiceImpl.findAll(1, 3).toList().size());
        verify(ruleRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll3() {
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
        assertEquals(2, ruleServiceImpl.findAll(1, 3).toList().size());
        verify(ruleRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll4() {
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenThrow(new InvalidEntityException("An error occurred"));
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.findAll(1, 3));
        verify(ruleRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll5() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category2);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(3);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(3);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule);
        ruleAttribute.setValue(10.0d);

        ArrayList<RuleAttribute> ruleAttributes = new ArrayList<>();
        ruleAttributes.add(ruleAttribute);

        Rule rule2 = new Rule();
        rule2.setCategory(category);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(ruleAttributes);
        rule2.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule2);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        assertEquals(1, ruleServiceImpl.findAll(1, 3).toList().size());
        verify(ruleRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#findAll(int, int)}
     */
    @Test
    void testFindAll6() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category2);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(3);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(3);
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

        Category category3 = new Category();
        category3.setId(2);
        category3.setName("com.talan.adminmodule.entity.Category");
        category3.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category3);
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

        RuleAttribute ruleAttribute2 = new RuleAttribute();
        ruleAttribute2.setAttribute(attribute2);
        ruleAttribute2.setId(2L);
        ruleAttribute2.setPercentage(0.5d);
        ruleAttribute2.setRule(rule2);
        ruleAttribute2.setValue(0.5d);

        ArrayList<RuleAttribute> ruleAttributes = new ArrayList<>();
        ruleAttributes.add(ruleAttribute2);
        ruleAttributes.add(ruleAttribute);

        Rule rule3 = new Rule();
        rule3.setCategory(category);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("The characteristics of someone or something");
        rule3.setEnabled(true);
        rule3.setId(1);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("Name");
        rule3.setRuleAttributes(ruleAttributes);
        rule3.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule3);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        assertEquals(1, ruleServiceImpl.findAll(1, 3).toList().size());
        verify(ruleRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#getModificationsByRuleId(Integer)}
     */
    @Test
    void testGetModificationsByRuleId() {
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
        assertTrue(ruleServiceImpl.getModificationsByRuleId(1).isEmpty());
        verify(ruleRepository).findById(Mockito.<Integer>any());
        verify(ruleModificationRepository).findByRuleOrderByModificationDateDesc(Mockito.<Rule>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#getModificationsByRuleId(Integer)}
     */
    @Test
    void testGetModificationsByRuleId2() {
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
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.getModificationsByRuleId(1));
        verify(ruleRepository).findById(Mockito.<Integer>any());
        verify(ruleModificationRepository).findByRuleOrderByModificationDateDesc(Mockito.<Rule>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#getModificationsByRuleId(Integer)}
     */
    @Test
    void testGetModificationsByRuleId3() {
        Optional<Rule> emptyResult = Optional.empty();
        when(ruleRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        assertThrows(EntityNotFoundException.class, () -> ruleServiceImpl.getModificationsByRuleId(1));
        verify(ruleRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules() {
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(ruleServiceImpl.searchRules(1, 3, "Query").toList().isEmpty());
        verify(ruleRepository).search(Mockito.<String>any(), Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules2() {
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
        assertEquals(1, ruleServiceImpl.searchRules(1, 3, "Query").toList().size());
        verify(ruleRepository).search(Mockito.<String>any(), Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules3() {
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
        assertEquals(2, ruleServiceImpl.searchRules(1, 3, "Query").toList().size());
        verify(ruleRepository).search(Mockito.<String>any(), Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules4() {
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any()))
                .thenThrow(new InvalidEntityException("An error occurred"));
        assertThrows(InvalidEntityException.class, () -> ruleServiceImpl.searchRules(1, 3, "Query"));
        verify(ruleRepository).search(Mockito.<String>any(), Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules5() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category2);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(3);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(3);
        rule.setName("Name");
        rule.setRuleAttributes(new ArrayList<>());
        rule.setRuleModifications(new ArrayList<>());

        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setAttribute(attribute);
        ruleAttribute.setId(1L);
        ruleAttribute.setPercentage(10.0d);
        ruleAttribute.setRule(rule);
        ruleAttribute.setValue(10.0d);

        ArrayList<RuleAttribute> ruleAttributes = new ArrayList<>();
        ruleAttributes.add(ruleAttribute);

        Rule rule2 = new Rule();
        rule2.setCategory(category);
        rule2.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setCreatedBy(1);
        rule2.setDescription("The characteristics of someone or something");
        rule2.setEnabled(true);
        rule2.setId(1);
        rule2.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule2.setLastModifiedBy(1);
        rule2.setName("Name");
        rule2.setRuleAttributes(ruleAttributes);
        rule2.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule2);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        assertEquals(1, ruleServiceImpl.searchRules(1, 3, "Query").toList().size());
        verify(ruleRepository).search(Mockito.<String>any(), Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link RuleServiceImpl#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules6() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Rule rule = new Rule();
        rule.setCategory(category2);
        rule.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setCreatedBy(3);
        rule.setDescription("The characteristics of someone or something");
        rule.setEnabled(true);
        rule.setId(1);
        rule.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule.setLastModifiedBy(3);
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

        Category category3 = new Category();
        category3.setId(2);
        category3.setName("com.talan.adminmodule.entity.Category");
        category3.setRules(new ArrayList<>());

        Rule rule2 = new Rule();
        rule2.setCategory(category3);
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

        RuleAttribute ruleAttribute2 = new RuleAttribute();
        ruleAttribute2.setAttribute(attribute2);
        ruleAttribute2.setId(2L);
        ruleAttribute2.setPercentage(0.5d);
        ruleAttribute2.setRule(rule2);
        ruleAttribute2.setValue(0.5d);

        ArrayList<RuleAttribute> ruleAttributes = new ArrayList<>();
        ruleAttributes.add(ruleAttribute2);
        ruleAttributes.add(ruleAttribute);

        Rule rule3 = new Rule();
        rule3.setCategory(category);
        rule3.setCreateDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setCreatedBy(1);
        rule3.setDescription("The characteristics of someone or something");
        rule3.setEnabled(true);
        rule3.setId(1);
        rule3.setLastModified(LocalDate.of(1970, 1, 1).atStartOfDay());
        rule3.setLastModifiedBy(1);
        rule3.setName("Name");
        rule3.setRuleAttributes(ruleAttributes);
        rule3.setRuleModifications(new ArrayList<>());

        ArrayList<Rule> content = new ArrayList<>();
        content.add(rule3);
        PageImpl<Rule> pageImpl = new PageImpl<>(content);
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        assertEquals(1, ruleServiceImpl.searchRules(1, 3, "Query").toList().size());
        verify(ruleRepository).search(Mockito.<String>any(), Mockito.<Pageable>any());
    }
}

