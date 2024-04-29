package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.talan.adminmodule.dto.AttributeDto;
import com.talan.adminmodule.entity.Attribute;
import com.talan.adminmodule.exception.EntityNotFoundException;
import com.talan.adminmodule.exception.InvalidEntityException;
import com.talan.adminmodule.repository.AttributeRepository;

import java.util.ArrayList;
import java.util.List;
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
     * Method under test: {@link AttributeServiceImpl#save(AttributeDto)}
     */
    @Test
    void testSave() {
        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());
        when(attributeRepository.save(Mockito.<Attribute>any())).thenReturn(attribute);
        AttributeDto attributeDTO = mock(AttributeDto.class);
        when(attributeDTO.getName()).thenReturn("Name");
        AttributeDto actualSaveResult = attributeServiceImpl.save(attributeDTO);
        assertEquals(1, actualSaveResult.getId().intValue());
        assertEquals("Name", actualSaveResult.getName());
        verify(attributeRepository).save(Mockito.<Attribute>any());
        verify(attributeDTO).getName();
    }

    /**
     * Method under test: {@link AttributeServiceImpl#save(AttributeDto)}
     */
    @Test
    void testSave2() {
        AttributeDto attributeDTO = mock(AttributeDto.class);
        when(attributeDTO.getName()).thenThrow(new InvalidEntityException("An error occurred"));
        assertThrows(InvalidEntityException.class, () -> attributeServiceImpl.save(attributeDTO));
        verify(attributeDTO).getName();
    }

    /**
     * Method under test: {@link AttributeServiceImpl#save(AttributeDto)}
     */
    @Test
    void testSave3() {
        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());
        when(attributeRepository.save(Mockito.<Attribute>any())).thenReturn(attribute);
        AttributeDto actualSaveResult = attributeServiceImpl.save(AttributeDto.builder().id(1).name("Name").build());
        assertEquals(1, actualSaveResult.getId().intValue());
        assertEquals("Name", actualSaveResult.getName());
        verify(attributeRepository).save(Mockito.<Attribute>any());
    }

    /**
     * Method under test: {@link AttributeServiceImpl#save(AttributeDto)}
     */
    @Test
    void testSave4() {
        when(attributeRepository.save(Mockito.<Attribute>any()))
                .thenThrow(new InvalidEntityException("An error occurred"));
        assertThrows(InvalidEntityException.class,
                () -> attributeServiceImpl.save(AttributeDto.builder().id(1).name("Name").build()));
        verify(attributeRepository).save(Mockito.<Attribute>any());
    }

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

    /**
     * Method under test: {@link AttributeServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete3() {
        Optional<Attribute> emptyResult = Optional.empty();
        when(attributeRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        assertThrows(EntityNotFoundException.class, () -> attributeServiceImpl.delete(1));
        verify(attributeRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link AttributeServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById() {
        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());
        Optional<Attribute> ofResult = Optional.of(attribute);
        when(attributeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        AttributeDto actualFindByIdResult = attributeServiceImpl.findById(1);
        assertEquals(1, actualFindByIdResult.getId().intValue());
        assertEquals("Name", actualFindByIdResult.getName());
        verify(attributeRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link AttributeServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById2() {
        Optional<Attribute> emptyResult = Optional.empty();
        when(attributeRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        assertThrows(EntityNotFoundException.class, () -> attributeServiceImpl.findById(1));
        verify(attributeRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link AttributeServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById3() {
        when(attributeRepository.findById(Mockito.<Integer>any()))
                .thenThrow(new InvalidEntityException("An error occurred"));
        assertThrows(InvalidEntityException.class, () -> attributeServiceImpl.findById(1));
        verify(attributeRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link AttributeServiceImpl#findAll()}
     */
    @Test
    void testFindAll() {
        when(attributeRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(attributeServiceImpl.findAll().isEmpty());
        verify(attributeRepository).findAll();
    }

    /**
     * Method under test: {@link AttributeServiceImpl#findAll()}
     */
    @Test
    void testFindAll2() {
        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        ArrayList<Attribute> attributeList = new ArrayList<>();
        attributeList.add(attribute);
        when(attributeRepository.findAll()).thenReturn(attributeList);
        List<AttributeDto> actualFindAllResult = attributeServiceImpl.findAll();
        assertEquals(1, actualFindAllResult.size());
        AttributeDto getResult = actualFindAllResult.get(0);
        assertEquals(1, getResult.getId().intValue());
        assertEquals("Name", getResult.getName());
        verify(attributeRepository).findAll();
    }

    /**
     * Method under test: {@link AttributeServiceImpl#findAll()}
     */
    @Test
    void testFindAll3() {
        Attribute attribute = new Attribute();
        attribute.setId(1);
        attribute.setName("Name");
        attribute.setRuleAttributes(new ArrayList<>());

        Attribute attribute2 = new Attribute();
        attribute2.setId(2);
        attribute2.setName("com.talan.adminmodule.entity.Attribute");
        attribute2.setRuleAttributes(new ArrayList<>());

        ArrayList<Attribute> attributeList = new ArrayList<>();
        attributeList.add(attribute2);
        attributeList.add(attribute);
        when(attributeRepository.findAll()).thenReturn(attributeList);
        List<AttributeDto> actualFindAllResult = attributeServiceImpl.findAll();
        assertEquals(2, actualFindAllResult.size());
        AttributeDto getResult = actualFindAllResult.get(0);
        assertEquals("com.talan.adminmodule.entity.Attribute", getResult.getName());
        AttributeDto getResult2 = actualFindAllResult.get(1);
        assertEquals("Name", getResult2.getName());
        assertEquals(1, getResult2.getId().intValue());
        assertEquals(2, getResult.getId().intValue());
        verify(attributeRepository).findAll();
    }

    /**
     * Method under test: {@link AttributeServiceImpl#findAll()}
     */
    @Test
    void testFindAll4() {
        when(attributeRepository.findAll()).thenThrow(new InvalidEntityException("An error occurred"));
        assertThrows(InvalidEntityException.class, () -> attributeServiceImpl.findAll());
        verify(attributeRepository).findAll();
    }

    /**
     * Method under test: {@link AttributeServiceImpl#existByName(String)}
     */
    @Test
    void testExistByName() {
        when(attributeRepository.existsByName(Mockito.<String>any())).thenReturn(true);
        assertTrue(attributeServiceImpl.existByName("Name"));
        verify(attributeRepository).existsByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AttributeServiceImpl#existByName(String)}
     */
    @Test
    void testExistByName2() {
        when(attributeRepository.existsByName(Mockito.<String>any())).thenReturn(false);
        assertFalse(attributeServiceImpl.existByName("Name"));
        verify(attributeRepository).existsByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AttributeServiceImpl#existByName(String)}
     */
    @Test
    void testExistByName3() {
        when(attributeRepository.existsByName(Mockito.<String>any()))
                .thenThrow(new InvalidEntityException("An error occurred"));
        assertThrows(InvalidEntityException.class, () -> attributeServiceImpl.existByName("Name"));
        verify(attributeRepository).existsByName(Mockito.<String>any());
    }
}

