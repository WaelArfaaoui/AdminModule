package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.talan.adminmodule.dto.CategoryDto;
import com.talan.adminmodule.entity.Category;
import com.talan.adminmodule.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;

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

@ContextConfiguration(classes = {CategoryServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    /**
     * Method under test: {@link CategoryServiceImpl#save(CategoryDto)}
     */
    @Test
    void testSave() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
        CategoryDto categoryDto = mock(CategoryDto.class);
        when(categoryDto.getId()).thenReturn(1);
        when(categoryDto.getName()).thenReturn("Name");
        CategoryDto actualSaveResult = categoryServiceImpl.save(categoryDto);
        assertEquals(1, actualSaveResult.getId().intValue());
        assertEquals("Name", actualSaveResult.getName());
        verify(categoryRepository).save(Mockito.<Category>any());
        verify(categoryDto).getId();
        verify(categoryDto).getName();
    }

    /**
     * Method under test: {@link CategoryServiceImpl#save(CategoryDto)}
     */
    @Test
    void testSave2() {
        CategoryDto categoryDto = mock(CategoryDto.class);
        when(categoryDto.getId()).thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class, () -> categoryServiceImpl.save(categoryDto));
        verify(categoryDto).getId();
    }

    /**
     * Method under test: {@link CategoryServiceImpl#save(CategoryDto)}
     */
    @Test
    void testSave3() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());
        when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
        CategoryDto actualSaveResult = categoryServiceImpl.save(CategoryDto.builder().id(1).name("Name").build());
        assertEquals(1, actualSaveResult.getId().intValue());
        assertEquals("Name", actualSaveResult.getName());
        verify(categoryRepository).save(Mockito.<Category>any());
    }

    /**
     * Method under test: {@link CategoryServiceImpl#save(CategoryDto)}
     */
    @Test
    void testSave4() {
        when(categoryRepository.save(Mockito.<Category>any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class,
                () -> categoryServiceImpl.save(CategoryDto.builder().id(1).name("Name").build()));
        verify(categoryRepository).save(Mockito.<Category>any());
    }

    /**
     * Method under test: {@link CategoryServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete() {
        doNothing().when(categoryRepository).deleteById(Mockito.<Integer>any());
        categoryServiceImpl.delete(1);
        verify(categoryRepository).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryServiceImpl#delete(Integer)}
     */
    @Test
    void testDelete2() {
        doThrow(new EntityNotFoundException("An error occurred")).when(categoryRepository)
                .deleteById(Mockito.<Integer>any());
        assertThrows(EntityNotFoundException.class, () -> categoryServiceImpl.delete(1));
        verify(categoryRepository).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());
        Optional<Category> ofResult = Optional.of(category);
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        CategoryDto actualFindByIdResult = categoryServiceImpl.findById(1);
        assertEquals(1, actualFindByIdResult.getId().intValue());
        assertEquals("Name", actualFindByIdResult.getName());
        verify(categoryRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById2() {
        Optional<Category> emptyResult = Optional.empty();
        when(categoryRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        assertThrows(EntityNotFoundException.class, () -> categoryServiceImpl.findById(1));
        verify(categoryRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryServiceImpl#findById(Integer)}
     */
    @Test
    void testFindById3() {
        when(categoryRepository.findById(Mockito.<Integer>any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class, () -> categoryServiceImpl.findById(1));
        verify(categoryRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link CategoryServiceImpl#findAll()}
     */
    @Test
    void testFindAll() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(categoryServiceImpl.findAll().isEmpty());
        verify(categoryRepository).findAll();
    }

    /**
     * Method under test: {@link CategoryServiceImpl#findAll()}
     */
    @Test
    void testFindAll2() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryDto> actualFindAllResult = categoryServiceImpl.findAll();
        assertEquals(1, actualFindAllResult.size());
        CategoryDto getResult = actualFindAllResult.get(0);
        assertEquals(1, getResult.getId().intValue());
        assertEquals("Name", getResult.getName());
        verify(categoryRepository).findAll();
    }

    /**
     * Method under test: {@link CategoryServiceImpl#findAll()}
     */
    @Test
    void testFindAll3() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setRules(new ArrayList<>());

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("com.talan.adminmodule.entity.Category");
        category2.setRules(new ArrayList<>());

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category2);
        categoryList.add(category);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<CategoryDto> actualFindAllResult = categoryServiceImpl.findAll();
        assertEquals(2, actualFindAllResult.size());
        CategoryDto getResult = actualFindAllResult.get(0);
        assertEquals("com.talan.adminmodule.entity.Category", getResult.getName());
        CategoryDto getResult2 = actualFindAllResult.get(1);
        assertEquals("Name", getResult2.getName());
        assertEquals(1, getResult2.getId().intValue());
        assertEquals(2, getResult.getId().intValue());
        verify(categoryRepository).findAll();
    }

    /**
     * Method under test: {@link CategoryServiceImpl#findAll()}
     */
    @Test
    void testFindAll4() {
        when(categoryRepository.findAll()).thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class, () -> categoryServiceImpl.findAll());
        verify(categoryRepository).findAll();
    }
}

