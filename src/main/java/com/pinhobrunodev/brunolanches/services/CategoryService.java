package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.entities.Category;
import com.pinhobrunodev.brunolanches.repositories.CategoryRepository;
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
public class CategoryService {

    @Autowired
    private CategoryRepository repository;


    @Transactional
    public void save(CategoryDTO dto) {
        Category entity = new Category();
        entity = copyDtoToEntity(dto, entity);
        repository.save(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = toUpdate(dto,id);
            repository.save(entity);
            return new CategoryDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID not found : " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return repository.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return repository.findById(id).map(CategoryDTO::new).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findByName(String name) {
        Category aux = repository.findByName(name.substring(0, 1).toUpperCase().concat(name.substring(1)));
        if (aux == null) {
            throw new ResourceNotFoundException("Name not found : " + name);
        }
        return new CategoryDTO(aux);

    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found : " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database violation");
        }
    }


    // Auxiliary methods
    public Category copyDtoToEntity(CategoryDTO dto, Category entity) {
        entity.setName(dto.getName());
        return entity;
    }

    public Category toUpdate(CategoryDTO dto, Long id) {
        Category aux = repository.getById(id);
        copyDtoToEntity(dto, aux);
        return copyDtoToEntity(dto, aux);
    }


}
