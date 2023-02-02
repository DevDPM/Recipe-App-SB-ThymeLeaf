package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.repository.RecipeRepository;
import com.springframework.recipeapp.service.IngredientService;
import com.springframework.recipeapp.service.RecipeService;
import com.springframework.recipeapp.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @Mock
    IngredientService ingredientService;
    @Mock
    Model model;

    IngredientController ingredientController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService,unitOfMeasureService,ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getIngredientByRecipeId() {
        // todo
    }

    @Test
    void saveOrUpdate() {
        // todo
    }

    @Test
    void updateById() {
        // todo
    }

    @Test
    void deleteById() {
        // todo
    }

    @Test
    public void testgetIngredientByRecipeId_NumberFormatException() throws Exception {
        mockMvc.perform(get("/recipe/xx/ingredients/show"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("error/400error"));

    }

    @Test
    public void testsaveOrUpdate_MissingHiddenFormPOSTValues() throws Exception {
        mockMvc.perform(get("/recipe/xx/ingredient/add"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("error/400error_missinghiddenformvalues"));

    }

    @Test
    public void testupdateById_MissingHiddenFormPOSTValues() throws Exception {
        mockMvc.perform(get("/recipe/xx/ingredient/xx/update"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("error/400error_missinghiddenformvalues"));

    }

    @Test
    public void testdeleteById_NumberFormatException() throws Exception {
        mockMvc.perform(get("/recipe/xx/ingredient/xx/delete"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("error/400error"));

    }

}