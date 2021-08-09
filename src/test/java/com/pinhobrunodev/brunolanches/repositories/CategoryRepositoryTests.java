package com.pinhobrunodev.brunolanches.repositories;


import com.pinhobrunodev.brunolanches.entities.Category;
import com.pinhobrunodev.brunolanches.factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class CategoryRepositoryTests {


    @Autowired
    private CategoryRepository repository;

    private Long totalCategories;
    private Long existingId;
    private Long nonExistingId;
    private String existingName;
    private String nonExistingName;

    @BeforeEach
    public void setUp() throws Exception {
        existingId = 1L;
        totalCategories = 3L;
        nonExistingId = 999L;
        existingName = "Pizza";
        nonExistingName = "Bob";
    }

    @Test
    public void findByIdShouldReturnOptionalWhenValidId() {
        Optional<Category> cat = repository.findById(existingId);
        Assertions.assertFalse(cat.isEmpty());
    }

    @Test
    public void findByIdShouldNotReturnOptionalWhenInvalidId() {
        Optional<Category> cat = repository.findById(nonExistingId);
        Assertions.assertTrue(cat.isEmpty());
    }


    @Test
    public void findByNameShouldReturnObjectWhenValidExistingName() {
        Category cat = repository.findByName(existingName);
        Assertions.assertNotNull(cat);
    }

    @Test
    public void findByNameShouldReturnNullObjectWhenNonExistsName() {
        Category cat = repository.findByName(nonExistingName);
        Assertions.assertNull(cat);
    }


    @Test
    public void saveShouldPersist() {
        Category cat = Factory.createCategory();
        repository.save(cat);
        Assertions.assertEquals(totalCategories+1, cat.getId());
    }

    @Test
    public void deleteByIdShouldDeleteWhenExistsId() {
        repository.deleteById(existingId);
        Optional<Category> result = repository.findById(existingId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteByIdShouldThrowEmptyResultDataAccessExceptionWhenNonExistingId() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }


}
