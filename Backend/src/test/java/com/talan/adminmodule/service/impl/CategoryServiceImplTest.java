package com.talan.adminmodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.talan.adminmodule.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
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
}

