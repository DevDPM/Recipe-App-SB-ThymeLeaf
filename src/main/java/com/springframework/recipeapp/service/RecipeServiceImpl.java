package com.springframework.recipeapp.service;

import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {

        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipe -> { recipeSet.add(recipe); });
        return recipeSet;
    }

    @Override
    public Recipe getRecipeById(Long id) {
//        return Optional.ofNullable(recipeRepository.findById(id)).get().get(); // can be more sophisticated
        return recipeRepository.findById(id).orElse(null);
    }
}
