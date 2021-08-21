package com.pinhobrunodev.brunolanches.resources;


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

@AutoConfigureMockMvc
@SpringBootTest
public class CategoryResourceDataIntegrityIT {


    @Autowired
    private MockMvc mockMvc;

    private Long dependentId;

    @BeforeEach
    void setUp() throws Exception {
        dependentId = 1L;
    }

    @Test
    void deleteShouldReturnBadRequestWhenDependentId() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/categories/delete/{id}", dependentId).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
