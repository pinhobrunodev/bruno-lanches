package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.entities.Category;
import com.pinhobrunodev.brunolanches.repositories.CategoryRepository;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class CategoryServiceIT {

    @Autowired
    private CategoryService service;
    @Autowired
    private CategoryRepository repository;

    private Long validId;
    private Long invalidId;
    private Long dependentId;
    private Long totalCategoryQuantity;
    private String validName;
    private String invalidName;
    private CategoryDTO dto;

    @BeforeEach
    void setUp() throws Exception {
        dto = new CategoryDTO(null, "Category Test");
        validId = 1L;
        invalidId = 999L;
        dependentId = 1L;
        totalCategoryQuantity = 5L;
        validName = "PASTA";
        invalidName = "APPLE";
    }


    @Test
    public void saveShouldPersist() {
        service.save(dto);
        Assertions.assertEquals(totalCategoryQuantity + 1, repository.count());
    }

    @Test
    public void updateShouldReturnCategoryDTOWhenIdExists() {
        String expectedName = dto.getName();
        CategoryDTO result = service.update(validId, dto);
        Assertions.assertEquals(expectedName, result.getName());
        Assertions.assertEquals(1L, result.getId());

    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(invalidId, dto);
        });
    }

    @Test
    public void findAllShouldNotReturnEmptyList() {
        List<CategoryDTO> result = service.findAll();
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findByIdShouldReturnCategoryDTOWhenIdExists() {
        CategoryDTO dto = service.findById(validId);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals("PASTA", dto.getName());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(invalidId);
        });
    }


    @Test
    public void findByNameShouldReturnCategoryDTOWhenValidName() {
        CategoryDTO result = service.findByName(validName);
        Assertions.assertNotNull(result);
    }

    @Test
    public void findByNameShouldNotReturnCategoryDTOWhenInvalidName() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findByName(invalidName);
        });
    }

    @Test
    public void deleteShouldDeleteWhenValidId() {
        service.delete(validId);
        Optional<Category> result = repository.findById(validId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(invalidId);
        });
    }



}