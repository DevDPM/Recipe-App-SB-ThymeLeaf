package com.springframework.recipeapp.service;

import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe getRecipeById(Long id);

    RecipeCommand findCommandById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    void deleteRecipeById(Long id);
}
