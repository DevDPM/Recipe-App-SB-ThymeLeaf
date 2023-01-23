package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    RecipeController recipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
    }

    @Test
    void getRecipeByIdPage() throws Exception {
        String ID = "5";

        //given
        Recipe recipe = new Recipe();
        recipe.setId(Long.parseLong(ID));

        when(recipeService.getRecipeById(Long.parseLong(ID)))
                .thenReturn(recipe);


        ArgumentCaptor argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        assertEquals("recipes/show", recipeController.getRecipeByIdPage(ID, model));

        verify(model, times(1))
                .addAttribute(ArgumentMatchers.eq("recipe"), argumentCaptor.capture());

        verify(recipeService, times(1))
                .getRecipeById(anyLong());


        // MockMVC tests
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/" + ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
    }
}












