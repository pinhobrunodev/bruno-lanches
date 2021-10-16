package com.pinhobrunodev.brunolanches.dto.category;

import com.pinhobrunodev.brunolanches.entities.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDTO {

    private Long id;
    @NotBlank(message = "Mandatory field")
    private String name;

    public CategoryDTO() {

    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
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
}