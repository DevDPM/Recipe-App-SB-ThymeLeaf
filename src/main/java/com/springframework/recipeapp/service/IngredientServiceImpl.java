package com.springframework.recipeapp.service;

import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.converter.IngredientCommandToIngredient;
import com.springframework.recipeapp.converter.IngredientToIngredientCommand;
import com.springframework.recipeapp.converter.RecipeToRecipeCommand;
import com.springframework.recipeapp.model.Ingredient;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.repository.RecipeRepository;
import com.springframework.recipeapp.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand toRecipeCommand;
    private final IngredientToIngredientCommand toIngredientCommand;
    private final IngredientCommandToIngredient toIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 RecipeToRecipeCommand toRecipeCommand,
                                 IngredientToIngredientCommand toIngredientCommand,
                                 IngredientCommandToIngredient toIngredient,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.toRecipeCommand = toRecipeCommand;
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
        if (ingredientReturn.getRecipe() == null)
            ingredientReturn.setRecipe(recipe);

        ingredientReturn.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                .orElseThrow(() -> new RuntimeException("Unit of measure not found")));

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

    @Override
    @Transactional
    public RecipeCommand deleteById(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent())
            throw new RuntimeException("Recipe not found");

        if (!recipeOptional.get().getId().equals(recipeId))
            throw new RuntimeException("Failed retrieving recipe");


        Recipe saveRecipe = recipeOptional.get();
        Optional<Ingredient> ingredientSet = saveRecipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if (!ingredientSet.isPresent())
            throw new RuntimeException("Failed to retrieve ingredient from recipe list");

        Ingredient ingredientToDelete = ingredientSet.get();
        ingredientToDelete.setRecipe(null);

        saveRecipe.getIngredients().remove(ingredientSet.get());

        Recipe savedRecipe = recipeRepository.save(saveRecipe);

        Optional<Ingredient> validateIngredient = savedRecipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if (validateIngredient.isPresent())
            throw new RuntimeException("failed removing ingredient");

        return toRecipeCommand.convert(savedRecipe);
    }
}
