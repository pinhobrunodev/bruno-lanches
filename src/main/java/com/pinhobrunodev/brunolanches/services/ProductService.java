package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;
import com.pinhobrunodev.brunolanches.entities.Category;
import com.pinhobrunodev.brunolanches.entities.Product;
import com.pinhobrunodev.brunolanches.repositories.CategoryRepository;
import com.pinhobrunodev.brunolanches.repositories.ProductRepository;
import com.pinhobrunodev.brunolanches.services.exceptions.DatabaseException;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional
    public void save(ProductDTO dto) {
        Product entity = new Product();
        entity = dtoToEntity(dto, entity);
        repository.save(entity);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {
        try {
            Product entity = new Product();
            entity = updateProduct(id, dto);
            return new ProductDTO(entity, entity.getCategories());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found : " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found :" + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database violation");
        }
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return repository.findAll().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product aux = repository.getById(id);
        return repository
                .findById(id)
                .map(x -> new ProductDTO(aux, aux.getCategories()))
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional(readOnly = true)
    public ProductDTO findByName(String name) {
        Product aux = repository.findByName(name);
        if (aux == null)
            throw new ResourceNotFoundException("Name not found : " + name);
        return new ProductDTO(aux, aux.getCategories());
    }


    //Auxiliary methods

    public Product dtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        for (CategoryDTO categoryDTO : dto.getCategories()) {
            Category aux = categoryRepository.getById(categoryDTO.getId());
            entity.getCategories().add(aux);
        }
        return entity;
    }

    public Product updateProduct(Long id, ProductDTO dto) {
        Product aux = repository.getById(id);
        return dtoToEntity(dto, aux);
    }

}
