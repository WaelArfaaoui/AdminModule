package com.talan.adminmodule.controller;

import static org.mockito.Mockito.when;

import com.talan.adminmodule.service.AttributeService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AttributeController.class})
@ExtendWith(SpringExtension.class)
class AttributeControllerTest {
    @Autowired
    private AttributeController attributeController;

    @MockBean
    private AttributeService attributeService;

    /**
     * Method under test: {@link AttributeController#getAllAttributes()}
     */
    @Test
    void testGetAllAttributes() throws Exception {
        when(attributeService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/attributes");
        MockMvcBuilders.standaloneSetup(attributeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

