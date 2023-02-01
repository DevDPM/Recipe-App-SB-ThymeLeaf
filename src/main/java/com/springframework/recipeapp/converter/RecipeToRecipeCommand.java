package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.CategoryCommand;
import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.command.NotesCommand;
import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand toNotesCommand;
    private final IngredientToIngredientCommand toIngredientCommand;
    private final CategoryToCategoryCommand toCategoryCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand toNotesCommand,
                                 IngredientToIngredientCommand toIngredientCommand,
                                 CategoryToCategoryCommand toCategoryCommand) {
        this.toNotesCommand = toNotesCommand;
        this.toIngredientCommand = toIngredientCommand;
        this.toCategoryCommand = toCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null)
            return null;

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setImage(source.getImage());
        recipeCommand.setNotes(toNotesCommand.convert(source.getNotes()));
        recipeCommand.getNotes().setRecipe(recipeCommand);
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());

        if (!source.getIngredients().isEmpty()) {
            source.getIngredients()
                    .stream()
                    .forEach(ingredient -> {
                        IngredientCommand ingredientCommand = toIngredientCommand.convert(ingredient);
                        ingredientCommand.setRecipe(recipeCommand);
                        recipeCommand.getIngredients().add(ingredientCommand);
                    });
        }

        if (!source.getCategories().isEmpty()) {
            source.getCategories()
                    .stream()
                    .forEach(category -> {
                        CategoryCommand categoryCommand = toCategoryCommand.convert(category);
                        categoryCommand.getRecipes().add(recipeCommand);
                        recipeCommand.getCategories().add(categoryCommand);
                    });
        }

        return recipeCommand;
    }
}
