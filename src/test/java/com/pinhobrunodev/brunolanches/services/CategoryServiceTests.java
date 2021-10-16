package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.entities.Category;
import com.pinhobrunodev.brunolanches.factory.Factory;
import com.pinhobrunodev.brunolanches.repositories.CategoryRepository;
import com.pinhobrunodev.brunolanches.services.exceptions.DatabaseException;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private String existingName;
    private Category category;
    private String nonExistingName;

    @BeforeEach
    public void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 9999L;
        dependentId = 2L;
        existingName = "Pizza";
        nonExistingName = "Bob";
        category = Factory.createCategory();


        Mockito.when(repository.getOne(existingId)).thenReturn(category);
        Mockito.when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(category));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);


        Mockito.when(repository.findByName(existingName)).thenReturn(category);
        Mockito.when(repository.findByName(nonExistingName)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    public void findByNameShouldReturnCategoryWhenValidName() {
        Category dto = repository.findByName(existingName);
        verify(repository, times(1)).findByName(existingName);
    }

    @Test
    public void findByNameShouldThrowsResourceNotFoundExceptionWhenInvalidName() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findByName(nonExistingName);
        });
        verify(repository, times(1)).findByName(nonExistingName);

    }

    @Test
    public void findByIdShouldReturnCategoryDTOWhenValidId() {
        CategoryDTO dto = service.findById(existingId);
        verify(repository, times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void updateShouldReturnACategoryDTOWhenValidId() {
        service.update(existingId, Factory.createCategoryDTO());
        Mockito.verify(repository, times(1)).getOne(existingId);
    }

    @Test
    public void updateShouldThrowsResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, Factory.createCategoryDTO());
        });
        Mockito.verify(repository, times(1)).getOne(nonExistingId);

    }

    @Test
    public void deleteShouldDelete() {
        service.delete(existingId);
        Mockito.verify(repository, times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowsResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        verify(repository, times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowsDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
        verify(repository, times(1)).deleteById(dependentId);
    }


}
