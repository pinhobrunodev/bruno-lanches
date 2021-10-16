package com.pinhobrunodev.brunolanches.dto.product;

import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.entities.Category;
import com.pinhobrunodev.brunolanches.entities.Product;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDTO {
    private Long id;
    @NotBlank(message = "Mandatory field")
    private String name;
    @NotBlank(message = "Mandatory field")
    private String description;
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price value must be higher than 0")
    @DecimalMin(value ="0.00",message = " Input the price of the product like that Ex: 9.99")
    @Digits(integer = 6, fraction = 2,message = " Higher than limit")
    private Double price;
    private final List<CategoryDTO> categories = new ArrayList<>();

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
