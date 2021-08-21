package com.pinhobrunodev.brunolanches.dto.product;

import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.entities.Category;
import com.pinhobrunodev.brunolanches.entities.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDTO {
    private Long id;
    @NotBlank(message = "Mandatory field")
    @Size(min = 6, max = 20, message = "Name must be between 6 and 20 digits")
    private String name;
    @NotBlank(message = "Mandatory field")
    @Size(min = 10, max = 60, message = "Description must be between 10 and 60 digits")
    private String description;
    @NotBlank(message = "Mandatory field")
    @Positive(message = "Price value must be higher tan 0")
    private Double price;
    @NotBlank(message = "Mandatory field")
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {

    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
    }

    public ProductDTO(Product entity, Set<Category> categoryEntity) {
        this(entity);
        categoryEntity.forEach(x -> categories.add(new CategoryDTO(x)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

}
