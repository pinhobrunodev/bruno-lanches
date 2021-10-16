package com.pinhobrunodev.brunolanches.resources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.factory.Factory;
import com.pinhobrunodev.brunolanches.services.CategoryService;
import com.pinhobrunodev.brunolanches.services.exceptions.DatabaseException;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(CategoryResource.class)
public class CategoryResourceTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private CategoryDTO categoryDTO;

    private Long validId;
    private Long invalidId;
    private Long dependentId;

    private String validName;
    private String invalidName;

    @MockBean
    private CategoryService service;


    private List<CategoryDTO> list;

    @BeforeEach
    public void setUp() throws Exception {
        validId = 1L;
        invalidId = 2L;
        dependentId = 3L;
        categoryDTO = Factory.createCategoryDTO();
        validName = "PASTA";
        invalidName = "invalidName";
        list = List.of(categoryDTO);

        Mockito.doNothing().when(service).delete(validId);
        Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(invalidId);
        Mockito.doThrow(DatabaseException.class).when(service).delete(dependentId);

        Mockito.doNothing().when(service).save(ArgumentMatchers.any());

        Mockito.when(service.update(ArgumentMatchers.eq(validId), ArgumentMatchers.any())).thenReturn(categoryDTO);
        Mockito.when(service.update(ArgumentMatchers.eq(invalidId), ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);

        Mockito.when(service.findById(validId)).thenReturn(categoryDTO);
        Mockito.when(service.findById(invalidId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(service.findByName(validName)).thenReturn(categoryDTO);
        Mockito.when(service.findByName(invalidName)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(service.findAll()).thenReturn(list);
    }

    @Test
    public void findAllCategories() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categories").accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findByNameShouldReturnCategoryDTOWhenValidName() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categories/find-by-name?name={name}", validName).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldReturn404HTTPStatusWhenNameDoesNotExists() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categories/find-by-name/{name}", invalidName).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnCategoryDTOWhenValidId() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categories/find-by-id/{id}", validId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldReturn404HTTPStatusWhenIdDoesNotExists() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categories/find-by-id/{id}", invalidId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateShouldReturnCategoryDTO() throws Exception {
        String jsonBody = mapper.writeValueAsString(categoryDTO);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/categories/update/{id}", validId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
    }

    @Test
    public void updateShouldReturn404HTTPStatusWhenIdDoesNotExists() throws Exception {
        String jsonBody = mapper.writeValueAsString(categoryDTO);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/categories/update/{id}", invalidId).content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void saveShouldThrow201HTTPStatus() throws Exception {
        String jsonBody = mapper.writeValueAsString(categoryDTO);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/categories/save").content(jsonBody).contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    public void deleteShouldDeleteWhenValidIdAndThrow204HTTPStatus() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/categories/delete/{id}", validId));
        result.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deleteShouldThrow404HTTPStatusWhenIdDoesNotExists() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/categories/delete/{id}", invalidId));
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteShouldThrow400HTTPStatusWhenIdIsDependent() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/categories/delete/{id}", dependentId));
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
