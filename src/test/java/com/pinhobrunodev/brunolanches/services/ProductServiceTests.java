package com.pinhobrunodev.brunolanches.services;


import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;
import com.pinhobrunodev.brunolanches.entities.Category;
import com.pinhobrunodev.brunolanches.entities.Product;
import com.pinhobrunodev.brunolanches.factory.Factory;
import com.pinhobrunodev.brunolanches.repositories.CategoryRepository;
import com.pinhobrunodev.brunolanches.repositories.ProductRepository;
import com.pinhobrunodev.brunolanches.services.exceptions.DatabaseException;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
public class ProductServiceTests {


    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;
    @Mock
    private CategoryRepository categoryRepository;


    private PageImpl<Product> page;

    private Long validId;
    private Long invalidId;
    private Long dependentId;

    private Category category;
    private Product product;
    private ProductDTO dto;


    @BeforeEach
    public void setUp() throws Exception {
        validId = 3L;
        invalidId = 999L;
        dependentId = 1L;
        product = Factory.createProduct();
        category = Factory.createCategory();
        page = new PageImpl<>(List.of(product));


        Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(repository.getById(validId)).thenReturn(product);
        Mockito.when(repository.getById(invalidId)).thenThrow(EntityNotFoundException.class);
        Mockito.when(categoryRepository.getById(validId)).thenReturn(category);
        Mockito.when(categoryRepository.getById(invalidId)).thenThrow(EntityNotFoundException.class);

        Mockito.doNothing().when(repository).deleteById(validId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(invalidId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        Mockito.when(repository.findById(validId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(invalidId)).thenReturn(Optional.empty());
    }

    @Test
    public void deleteShouldDeleteWhenValidId() {
        service.delete(validId);
        Mockito.verify(repository, Mockito.times(1)).deleteById(validId);
    }

    @Test
    public void deleteShouldThrowsResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(invalidId);
        });
        Mockito.verify(repository, Mockito.times(1)).deleteById(invalidId);
    }

    @Test
    public void deleteShouldThrowsDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void pagedSearchShouldReturnProductDTOPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductDTO> dto = service.pagedSearch(pageable);
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
    }

   /*
    @Test
    public void updateShouldReturnProductDTOWhenValidId() {
        service.update(Factory.createProductDTO(), validId);
        Mockito.verify(repository, Mockito.times(1)).getById(validId);
    }
    */


    @Test
    public void findByIdShouldReturnProductDTOWhenValidId() {
        ProductDTO dto = service.findById(validId);
        verify(repository, times(1)).findById(validId);
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(invalidId);
        });
    }

    @Test
    public void updateShouldThrowsResourceNotFoundExceptionWhenInvalidId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(Factory.createProductDTO(), invalidId);
        });
    }


}
