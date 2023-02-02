package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.exception.NotFoundException;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.repository.RecipeRepository;
import com.springframework.recipeapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;


    @Mock
    Model model;

    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }


    @Test
    public void getRecipeByIdTstNotFound() throws Exception {
        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturn = recipeService.findById(1L);
    }

    @Test
    public void testGetRecipeNotfound() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getRecipeById() throws Exception {
        Long ID = 5L;

        //given
        Recipe recipe = new Recipe();
        recipe.setId(ID);

        when(recipeService.findById(ID))
                .thenReturn(recipe);


        ArgumentCaptor argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        assertEquals("recipe/show", recipeController.getRecipeByIdPage(ID, model));

        verify(model, times(1))
                .addAttribute(ArgumentMatchers.eq("recipe"), argumentCaptor.capture());

        verify(recipeService, times(1))
                .findById(anyLong());


        // MockMVC tests

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + ID + "/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    void testGetNewRecipeForm() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    void testPostNewRecipeForm() throws Exception {
        Long ID = 1L;
        RecipeCommand command = new RecipeCommand();
        command.setId(ID);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/" + ID + "/show"));
    }

    @Test
    void testGetUpdateView() throws Exception {
        Long ID = 1L;
        RecipeCommand command = new RecipeCommand();
        command.setId(ID);

        when(recipeService.findCommandById(ID)).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + ID + "/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

    }

    @Test
    void testDeleteRecipeById() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + anyLong() + "/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));

        verify(recipeService, times(1)).deleteRecipeById(anyLong());
    }
}












