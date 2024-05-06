package com.talan.adminmodule.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.adminmodule.dto.CategoryDto;
import com.talan.adminmodule.dto.RuleDto;
import com.talan.adminmodule.entity.Category;
import com.talan.adminmodule.entity.Rule;
import com.talan.adminmodule.entity.RuleModification;
import com.talan.adminmodule.repository.AttributeRepository;
import com.talan.adminmodule.repository.CategoryRepository;
import com.talan.adminmodule.repository.RuleAttributeRepository;
import com.talan.adminmodule.repository.RuleModificationRepository;
import com.talan.adminmodule.repository.RuleRepository;
import com.talan.adminmodule.service.RuleService;
import com.talan.adminmodule.service.impl.RuleServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RuleController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RuleControllerDiffblueTest {
    @Autowired
    private RuleController ruleController;

    @MockBean
    private RuleService ruleService;

    /**
     * Method under test: {@link RuleController#saveRule(RuleDto)}
     */
    @Test
    void testSaveRule() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
        RuleRepository ruleRepository = mock(RuleRepository.class);
        when(ruleRepository.save(Mockito.<Rule>any())).thenReturn(rule);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");
        category2.setRules(new ArrayList<>());

        Category category3 = new Category();
        category3.setId(1);
        category3.setName("Name");
        category3.setRules(new ArrayList<>());
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
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
        RuleModificationRepository ruleModificationRepository = mock(RuleModificationRepository.class);
        when(ruleModificationRepository.save(Mockito.<RuleModification>any())).thenReturn(ruleModification);
        RuleController ruleController = new RuleController(
                new RuleServiceImpl(ruleRepository, mock(AttributeRepository.class), categoryRepository,
                        ruleModificationRepository, mock(RuleAttributeRepository.class)));
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
        ResponseEntity<RuleDto> actualSaveRuleResult = ruleController.saveRule(ruleDto);

        // Assert
        verify(categoryRepository).findByName("Name");
        verify(ruleRepository).save(isA(Rule.class));
        verify(ruleModificationRepository).save(isA(RuleModification.class));
        RuleDto body = actualSaveRuleResult.getBody();
        assertEquals("1970-01-01", body.getCreateDate().toLocalDate().toString());
        assertEquals("1970-01-01", body.getLastModified().toLocalDate().toString());
        CategoryDto category6 = body.getCategory();
        assertEquals("Name", category6.getName());
        assertEquals("Name", body.getName());
        assertEquals("The characteristics of someone or something", body.getDescription());
        assertEquals(1, category6.getId().intValue());
        assertEquals(1, body.getCreatedBy().intValue());
        assertEquals(1, body.getId().intValue());
        assertEquals(1, body.getLastModifiedBy().intValue());
        assertEquals(201, actualSaveRuleResult.getStatusCodeValue());
        assertTrue(body.isEnabled());
        assertTrue(body.getAttributeDtos().isEmpty());
        assertTrue(actualSaveRuleResult.getHeaders().isEmpty());
        assertEquals(ruleDto, body);
    }

    /**
     * Method under test: {@link RuleController#updateStatus(Integer, boolean)}
     */
    @Test
    void testUpdateStatus() throws Exception {
        // Arrange
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        CategoryDto category = CategoryDto.builder().id(1).name("Name").build();
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult.category(category);
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto buildResult = idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay())
                .lastModifiedBy(1)
                .name("Name")
                .build();
        when(ruleService.updateStatus(Mockito.<Integer>any(), anyBoolean())).thenReturn(buildResult);
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/api/rules/{id}/status", 1);
        MockHttpServletRequestBuilder requestBuilder = putResult.param("enabled", String.valueOf(true));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(ruleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"category\":{\"id\":1"
                                        + ",\"name\":\"Name\"},\"enabled\":true,\"createDate\":[1970,1,1,0,0],\"lastModified\":[1970,1,1,0,0],\"createdBy\""
                                        + ":1,\"lastModifiedBy\":1,\"attributeDtos\":[]}"));
    }

    /**
     * Method under test:
     * {@link RuleController#updateRule(Integer, Integer, String, RuleDto)}
     */
    @Test
    void testUpdateRule() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        RuleService ruleService = mock(RuleService.class);
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        CategoryDto category = CategoryDto.builder().id(1).name("Name").build();
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult.category(category);
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto buildResult = idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay())
                .lastModifiedBy(1)
                .name("Name")
                .build();
        when(ruleService.updateRule(Mockito.<Integer>any(), Mockito.<RuleDto>any(), Mockito.<String>any(),
                Mockito.<Integer>any())).thenReturn(buildResult);

        // Act
        ResponseEntity<RuleDto> actualUpdateRuleResult = (new RuleController(ruleService)).updateRule(1, 1,
                "Mod Description", null);

        // Assert
        verify(ruleService).updateRule(eq(1), isNull(), eq("Mod Description"), eq(1));
        assertEquals(200, actualUpdateRuleResult.getStatusCodeValue());
        assertTrue(actualUpdateRuleResult.hasBody());
        assertTrue(actualUpdateRuleResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link RuleController#getModificationsByRuleId(Integer)}
     */
    @Test
    void testGetModificationsByRuleId() throws Exception {
        // Arrange
        when(ruleService.getModificationsByRuleId(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/rules/{id}/history", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(ruleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RuleController#deleteRule(Integer)}
     */
    @Test
    void testDeleteRule() throws Exception {
        // Arrange
        doNothing().when(ruleService).delete(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/rules/{id}", 1);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(ruleController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link RuleController#findAllRules(int, int)}
     */
    @Test
    void testFindAllRules() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        RuleRepository ruleRepository = mock(RuleRepository.class);
        when(ruleRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        ResponseEntity<Page<RuleDto>> actualFindAllRulesResult = (new RuleController(
                new RuleServiceImpl(ruleRepository, mock(AttributeRepository.class), mock(CategoryRepository.class),
                        mock(RuleModificationRepository.class), mock(RuleAttributeRepository.class)))).findAllRules(1, 3);

        // Assert
        verify(ruleRepository).findAll(isA(Pageable.class));
        assertEquals(200, actualFindAllRulesResult.getStatusCodeValue());
        assertTrue(actualFindAllRulesResult.getBody().toList().isEmpty());
        assertTrue(actualFindAllRulesResult.hasBody());
        assertTrue(actualFindAllRulesResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link RuleController#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        RuleRepository ruleRepository = mock(RuleRepository.class);
        when(ruleRepository.search(Mockito.<String>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        ResponseEntity<Page<RuleDto>> actualSearchRulesResult = (new RuleController(
                new RuleServiceImpl(ruleRepository, mock(AttributeRepository.class), mock(CategoryRepository.class),
                        mock(RuleModificationRepository.class), mock(RuleAttributeRepository.class)))).searchRules(1, 3, "Query");

        // Assert
        verify(ruleRepository).search(eq("Query"), isA(Pageable.class));
        assertEquals(200, actualSearchRulesResult.getStatusCodeValue());
        assertTrue(actualSearchRulesResult.getBody().toList().isEmpty());
        assertTrue(actualSearchRulesResult.hasBody());
        assertTrue(actualSearchRulesResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link RuleController#deleteRule(Integer)}
     */
    @Test
    void testDeleteRule2() throws Exception {
        // Arrange
        doNothing().when(ruleService).delete(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/rules/{id}", 1);
        requestBuilder.contentType("https://example.org/example");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(ruleController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link RuleController#findRuleById(Integer)}
     */
    @Test
    void testFindRuleById() throws Exception {
        // Arrange
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        CategoryDto category = CategoryDto.builder().id(1).name("Name").build();
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult.category(category);
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto buildResult = idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay())
                .lastModifiedBy(1)
                .name("Name")
                .build();
        when(ruleService.findById(Mockito.<Integer>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/rules/{id}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(ruleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"category\":{\"id\":1"
                                        + ",\"name\":\"Name\"},\"enabled\":true,\"createDate\":[1970,1,1,0,0],\"lastModified\":[1970,1,1,0,0],\"createdBy\""
                                        + ":1,\"lastModifiedBy\":1,\"attributeDtos\":[]}"));
    }


}
