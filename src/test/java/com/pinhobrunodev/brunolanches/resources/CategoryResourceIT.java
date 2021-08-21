package com.pinhobrunodev.brunolanches.resources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.factory.Factory;
import com.pinhobrunodev.brunolanches.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryResourceIT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private CategoryDTO dto;

    private Long validId;
    private Long invalidId;
    private String validName;
    private String invalidName;

    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        validId = 1L;
        invalidId = 9999L;
        validName = "PASTA";
        invalidName = "invalidName";
        dto = Factory.createCategoryDTO();
    }

    @Test
    void saveShouldPersistAndReturnHttpStatusCode201() throws Exception {
        dto.setId(null);
        String jsonBody = mapper.writeValueAsString(dto);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/categories/save").content(jsonBody).contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void updateShouldUpdateWhenValidIdAndReturnHttpStatusCode200() throws Exception {
        dto.setId(null);
        String jsonBody = mapper.writeValueAsString(dto);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/categories/update/{id}", validId).content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Eletronico"));
    }

    @Test
    void updateShouldReturnHttpStatusCode404WhenIdDoesNotExists() throws Exception {
        dto.setId(null);
        String jsonBody = mapper.writeValueAsString(dto);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/categories/update/{id}", invalidId).content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteShouldDeleteWhenIdExistsAndThrow204HttpStatusCode() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/categories/delete/{id}", validId).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteShouldNotDeleteWhenIdDoesNotExistAndThrow404HttpStatusCode() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/categories/delete/{id}", invalidId).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void findByIdShouldReturnCategoryDTOAndHttpStatus200WhenIdExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/categories/find-by-id/{id}", validId).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("PASTA"));
    }

    @Test
    void findByIdShouldReturnHttpStatus404WhenIdDoesNotExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/categories/find-by-id/{id}", invalidId).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void findByNameShouldReturnCategoryDTOAndHttpStatus200WhenNameExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/categories/find-by-name/{name}", validName).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("PASTA"));
    }

    @Test
    void findByNameShouldReturnHttpStatus404WhenNameDoesNotExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/categories/find-by-name/{name}", invalidName).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void findAllShouldReturnAllCategoriesAndHttpStatusCode200() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/categories").contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("PASTA"));
    }
}
