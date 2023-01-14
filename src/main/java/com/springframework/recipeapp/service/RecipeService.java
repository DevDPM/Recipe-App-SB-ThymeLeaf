package com.springframework.recipeapp.service;

import com.springframework.recipeapp.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
