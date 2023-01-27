package com.springframework.recipeapp.service;

import com.springframework.recipeapp.converter.RecipeCommandToRecipe;
import com.springframework.recipeapp.converter.RecipeToRecipeCommand;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    RecipeToRecipeCommand toRecipeCommand;
    RecipeCommandToRecipe toRecipe;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, toRecipeCommand, toRecipe);
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeSet);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);

        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById() {
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(id)).thenReturn(recipeOptional);

        Recipe receivedRecipe = recipeService.getRecipeById(id);

        assertNotNull(receivedRecipe, "Null recipes returned");
        assertEquals(id, receivedRecipe.getId());
        verify(recipeRepository, times(1)).findById(id);
    }


}