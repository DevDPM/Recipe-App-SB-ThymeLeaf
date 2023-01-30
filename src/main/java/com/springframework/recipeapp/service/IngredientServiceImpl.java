package com.springframework.recipeapp.service;

import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.converter.IngredientCommandToIngredient;
import com.springframework.recipeapp.converter.IngredientToIngredientCommand;
import com.springframework.recipeapp.model.Ingredient;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.repository.RecipeRepository;
import com.springframework.recipeapp.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand toIngredientCommand;
    private final IngredientCommandToIngredient toIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand toIngredientCommand,
                                 IngredientCommandToIngredient toIngredient,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.toIngredientCommand = toIngredientCommand;
        this.toIngredient = toIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            // todo error handling
            throw new RuntimeException("Recipe not found");
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> toIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            // todo error handling
            throw new RuntimeException("Ingredient not found in recipe");
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            //todo error handling
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if (!ingredientOptional.isPresent()) {
            Ingredient ingredient = toIngredient.convert(ingredientCommand);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Ingredient ingredientReturn = ingredientOptional.get();
        ingredientReturn.setDescription(ingredientCommand.getDescription());
        ingredientReturn.setAmount(ingredientCommand.getAmount());
        ingredientReturn.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                .orElseThrow(() -> new RuntimeException("Unit ofm easure not found")));

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if (!savedIngredientOptional.isPresent()) {
            // todo error handling
            throw new RuntimeException("Ingredient did not correctly save");
        }

        return toIngredientCommand.convert(savedIngredientOptional.get());
    }
}
