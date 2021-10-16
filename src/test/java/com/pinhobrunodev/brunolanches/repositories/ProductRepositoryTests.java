package com.pinhobrunodev.brunolanches.repositories;


import com.pinhobrunodev.brunolanches.entities.Product;
import com.pinhobrunodev.brunolanches.factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {


    @Autowired
    private ProductRepository repository;

    private Long validId;
    private Long invalidId;
    private String validName;
    private String invalidName;
    private Long quantity;

    @BeforeEach
    public void setUp() throws Exception {
        validId = 1L;
        invalidId = 999L;
        validName = "REDBULL";
        invalidName = "invalidName";
        quantity = 11L;
    }


    @Test
    public void saveShouldPersistWhenValidArguments() {
        Product aux = Factory.createProduct();
        repository.save(aux);
        Assertions.assertNotNull(aux.getCategories());
        Assertions.assertEquals(quantity + 1, aux.getId());
    }

    @Test
    public void findByIdShouldReturnOptionalWhenValidId() {
        Optional<Product> result = repository.findById(validId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldNotReturnOptionalWhenInvalidId() {
        Optional<Product> result = repository.findById(invalidId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteShouldDeleteWhenValidId() {
        repository.deleteById(validId);
        Optional<Product> result = repository.findById(validId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteShouldThrowsEmptyResultDataAcessExceptionWhenInvalidId() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(invalidId);
        });
    }

    @Test
    public void findByNameShouldReturnProductObjectWhenValidName() {
        Product aux = repository.findByName(validName);
        Assertions.assertEquals(validName, aux.getName());
    }

    @Test
    public void findByNameShouldReturnNullProductObjectWhenNonExistsName() {
        Product aux = repository.findByName(invalidName);
        Assertions.assertNull(aux);
    }
}
