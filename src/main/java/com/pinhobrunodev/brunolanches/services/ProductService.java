package com.pinhobrunodev.brunolanches.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;
import com.pinhobrunodev.brunolanches.entities.Category;
import com.pinhobrunodev.brunolanches.entities.Product;
import com.pinhobrunodev.brunolanches.repositories.CategoryRepository;
import com.pinhobrunodev.brunolanches.repositories.ProductRepository;
import com.pinhobrunodev.brunolanches.services.exceptions.DatabaseException;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import com.pinhobrunodev.brunolanches.util.ValidatingNumberOfCategories;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> pagedSearch(Pageable pageable) {
		Page<Product> list = repository.findAll(pageable);
		return list.map(ProductDTO::new);
	}

	@Transactional
	public void save(ProductDTO dto) {
		Product entity = new Product();
		entity = dtoToEntity(dto, entity);
		ValidatingNumberOfCategories.validating(entity);
		repository.save(entity);
	}

	@Transactional
	public ProductDTO update(ProductDTO dto, Long id) {
		try {
			Product entity = new Product();
			entity = updateProduct(id, dto);
			repository.save(entity);
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
		return repository.findById(id).map(x -> new ProductDTO(x, x.getCategories()))
				.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
	}

	@Transactional(readOnly = true)
	public ProductDTO findByName(String name) {
		Product aux = repository.findByName(name.substring(0, 1).toUpperCase().concat(name.substring(1)));
		if (aux == null)
			throw new ResourceNotFoundException("Name not found : " + name);
		return new ProductDTO(aux, aux.getCategories());
	}

	// Auxiliary methods

	public Product dtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.getCategories().clear();
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
