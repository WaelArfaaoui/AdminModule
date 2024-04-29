package com.talan.adminmodule.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RuleController.class})
@ExtendWith(SpringExtension.class)
class RuleControllerTest {
    @Autowired
    private RuleController ruleController;

    @MockBean
    private RuleService ruleService;

    /**
     * Method under test: {@link RuleController#saveRule(RuleDto)}
     */
    @Test
    void testSaveRule() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.talan.adminmodule.dto.RuleDto["createDate"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1308)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:479)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:318)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4719)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3964)
        //   See https://diff.blue/R013 to resolve this issue.

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
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult
                .category(CategoryDto.builder().id(1).name("Name").build());
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto ruleDto = idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay())
                .lastModifiedBy(1)
                .name("Name")
                .build();
        ResponseEntity<RuleDto> actualSaveRuleResult = ruleController.saveRule(ruleDto);
        RuleDto body = actualSaveRuleResult.getBody();
        assertEquals(ruleDto, body);
        assertTrue(actualSaveRuleResult.getHeaders().isEmpty());
        assertEquals(201, actualSaveRuleResult.getStatusCodeValue());
        assertEquals(rules, body.getAttributeDtos());
        assertEquals(1, body.getId().intValue());
        assertEquals("The characteristics of someone or something", body.getDescription());
        assertEquals(1, body.getCreatedBy().intValue());
        assertEquals("00:00", body.getCreateDate().toLocalTime().toString());
        assertTrue(body.isEnabled());
        assertEquals("Name", body.getName());
        assertEquals("1970-01-01", body.getLastModified().toLocalDate().toString());
        assertEquals(1, body.getLastModifiedBy().intValue());
        CategoryDto category5 = body.getCategory();
        assertEquals("Name", category5.getName());
        assertEquals(1, category5.getId().intValue());
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(ruleModificationRepository).save(Mockito.<RuleModification>any());
    }

    /**
     * Method under test: {@link RuleController#updateStatus(Integer, boolean)}
     */
    @Test
    void testUpdateStatus() throws Exception {
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult
                .category(CategoryDto.builder().id(1).name("Name").build());
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        when(ruleService.updateStatus(Mockito.<Integer>any(), anyBoolean())).thenReturn(
                idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay()).lastModifiedBy(1).name("Name").build());
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/api/rules/{id}/status", 1);
        MockHttpServletRequestBuilder requestBuilder = putResult.param("enabled", String.valueOf(true));
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
     * Method under test: {@link RuleController#updateRule(Integer, Integer, String, RuleDto)}
     */
    @Test
    void testUpdateRule() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.talan.adminmodule.dto.RuleDto["createDate"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1308)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:479)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:318)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4719)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3964)
        //   See https://diff.blue/R013 to resolve this issue.

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
        RuleRepository ruleRepository = mock(RuleRepository.class);
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
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
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
        RuleModificationRepository ruleModificationRepository = mock(RuleModificationRepository.class);
        when(ruleModificationRepository.save(Mockito.<RuleModification>any())).thenReturn(ruleModification);
        RuleAttributeRepository ruleAttributeRepository = mock(RuleAttributeRepository.class);
        doNothing().when(ruleAttributeRepository).deleteByRule(Mockito.<Rule>any());
        RuleController ruleController = new RuleController(new RuleServiceImpl(ruleRepository,
                mock(AttributeRepository.class), categoryRepository, ruleModificationRepository, ruleAttributeRepository));
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult
                .category(CategoryDto.builder().id(1).name("Name").build());
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        RuleDto updatedRuleDto = idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay())
                .lastModifiedBy(1)
                .name("Name")
                .build();
        ResponseEntity<RuleDto> actualUpdateRuleResult = ruleController.updateRule(1, 1, "Mod Description",
                updatedRuleDto);
        RuleDto body = actualUpdateRuleResult.getBody();
        assertEquals(updatedRuleDto, body);
        assertTrue(actualUpdateRuleResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdateRuleResult.getStatusCodeValue());
        assertEquals(rules, body.getAttributeDtos());
        assertEquals(1, body.getId().intValue());
        assertEquals("The characteristics of someone or something", body.getDescription());
        assertEquals(1, body.getCreatedBy().intValue());
        assertEquals("00:00", body.getCreateDate().toLocalTime().toString());
        assertTrue(body.isEnabled());
        assertEquals("Name", body.getName());
        assertEquals("1970-01-01", body.getLastModified().toLocalDate().toString());
        assertEquals(1, body.getLastModifiedBy().intValue());
        CategoryDto category6 = body.getCategory();
        assertEquals("Name", category6.getName());
        assertEquals(1, category6.getId().intValue());
        verify(ruleRepository).save(Mockito.<Rule>any());
        verify(ruleRepository).findById(Mockito.<Integer>any());
        verify(categoryRepository).findByName(Mockito.<String>any());
        verify(ruleModificationRepository).save(Mockito.<RuleModification>any());
        verify(ruleAttributeRepository).deleteByRule(Mockito.<Rule>any());
    }

    /**
     * Method under test: {@link RuleController#deleteRule(Integer)}
     */
    @Test
    void testDeleteRule() throws Exception {
        doNothing().when(ruleService).delete(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/rules/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(ruleController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link RuleController#getModificationsByRuleId(Integer)}
     */
    @Test
    void testGetModificationsByRuleId() throws Exception {
        when(ruleService.getModificationsByRuleId(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/rules/{id}/history", 1);
        MockMvcBuilders.standaloneSetup(ruleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RuleController#searchRules(int, int, String)}
     */
    @Test
    void testSearchRules() throws Exception {
        when(ruleService.searchRules(anyInt(), anyInt(), Mockito.<String>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/rules/search");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(ruleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    /**
     * Method under test: {@link RuleController#deleteRule(Integer)}
     */
    @Test
    void testDeleteRule2() throws Exception {
        doNothing().when(ruleService).delete(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/rules/{id}", 1);
        requestBuilder.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(ruleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link RuleController#findAllRules(int, int)}
     */
    @Test
    void testFindAllRules() throws Exception {
        when(ruleService.findAll(anyInt(), anyInt())).thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/rules");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(ruleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    /**
     * Method under test: {@link RuleController#findRuleById(Integer)}
     */
    @Test
    void testFindRuleById() throws Exception {
        RuleDto.RuleDtoBuilder builderResult = RuleDto.builder();
        RuleDto.RuleDtoBuilder attributeDtosResult = builderResult.attributeDtos(new ArrayList<>());
        RuleDto.RuleDtoBuilder categoryResult = attributeDtosResult
                .category(CategoryDto.builder().id(1).name("Name").build());
        RuleDto.RuleDtoBuilder idResult = categoryResult.createDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                .createdBy(1)
                .description("The characteristics of someone or something")
                .enabled(true)
                .id(1);
        when(ruleService.findById(Mockito.<Integer>any())).thenReturn(
                idResult.lastModified(LocalDate.of(1970, 1, 1).atStartOfDay()).lastModifiedBy(1).name("Name").build());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/rules/{id}", 1);
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

