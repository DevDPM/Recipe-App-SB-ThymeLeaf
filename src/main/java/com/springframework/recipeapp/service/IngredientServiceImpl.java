package com.springframework.recipeapp.service;

import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        return null;
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        return null;
    }
}
