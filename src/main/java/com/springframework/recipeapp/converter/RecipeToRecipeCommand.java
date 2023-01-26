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

        recipeCommand.setServings(null);
        if (source.getSource() != null)
            recipeCommand.setServings(Integer.valueOf(source.getServings()));
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setUrl(source.getUrl());

        recipeCommand.setPrepTime(null);
        if (source.getServings() != null)
            recipeCommand.setPrepTime(Integer.valueOf(source.getPrepTime()));

        recipeCommand.setCookTime(null);
        if (source.getCookTime() != null)
            recipeCommand.setCookTime(Integer.valueOf(source.getCookTime()));
        recipeCommand.setSource(source.getSource());

        Set<IngredientCommand> ingredientCommandSet = new HashSet<>();
        if (!source.getIngredients().isEmpty()) {
            source.getIngredients()
                    .stream()
                    .forEach(ingredient -> {
                        IngredientCommand ingredientCommand = toIngredientCommand.convert(ingredient);
                        ingredientCommand.setRecipe(recipeCommand);
                        ingredientCommandSet.add(ingredientCommand);
                    });
        }
        recipeCommand.setIngredients(ingredientCommandSet);

        if (source.getNotes() != null) {
            NotesCommand notesCommand = toNotesCommand.convert(source.getNotes());
            notesCommand.setRecipe(recipeCommand);
            recipeCommand.setNotes(notesCommand);
        }

        Set<CategoryCommand> categoryCommands = new HashSet<>();
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
