package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.talan.adminmodule.entity.Attribute;
import com.talan.adminmodule.exception.InvalidEntityException;
import com.talan.adminmodule.repository.AttributeRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AttributeServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AttributeServiceImplTest {
    @MockBean
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeServiceImpl attributeServiceImpl;

    /**
     * Method under test: {@link AttributeServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete() {
        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());
        Optional<Attribute> ofResult = Optional.of(attribute);
        doNothing().when(attributeRepository).deleteById(Mockito.<Integer>any());
        when(attributeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        attributeServiceImpl.delete(1);
        verify(attributeRepository).findById(Mockito.<Integer>any());
        verify(attributeRepository).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link AttributeServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete2() {
        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());
        Optional<Attribute> ofResult = Optional.of(attribute);
        doThrow(new InvalidEntityException("An error occurred")).when(attributeRepository)
                .deleteById(Mockito.<Integer>any());
        when(attributeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        assertThrows(InvalidEntityException.class, () -> attributeServiceImpl.delete(1));
        verify(attributeRepository).findById(Mockito.<Integer>any());
        verify(attributeRepository).deleteById(Mockito.<Integer>any());
    }
}

