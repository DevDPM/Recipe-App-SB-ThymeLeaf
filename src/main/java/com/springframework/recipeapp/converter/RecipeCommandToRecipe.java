package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.NotesCommand;
import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.model.Category;
import com.springframework.recipeapp.model.Ingredient;
import com.springframework.recipeapp.model.Notes;
import com.springframework.recipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory toCategory;
    private final IngredientCommandToIngredient toIngredient;
    private final NotesCommandToNotes toNotes;

    public RecipeCommandToRecipe(CategoryCommandToCategory toCategory,
                                 IngredientCommandToIngredient toIngredient,
                                 NotesCommandToNotes toNotes) {
        this.toCategory = toCategory;
        this.toIngredient = toIngredient;
        this.toNotes = toNotes;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null)
            return null;

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setImage(source.getImage());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDescription(source.getDescription());
        recipe.setDirections(source.getDirections());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setServings(source.getServings());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        System.out.println("ToRecipe Notes.getID: "+source.getNotes().getId());
        System.out.println("ToRecipe Notes.getID: "+source.getNotes().getRecipeNotes());
        System.out.println("ToRecipe Notes.getID: "+source.getNotes().getRecipe());
        recipe.setNotes(toNotes.convert(source.getNotes()));
        recipe.getNotes().setRecipe(recipe);

        if (!source.getIngredients().isEmpty()) {
            source.getIngredients()
                    .stream()
                    .forEach(ingredientCommand -> {
                        Ingredient ingredient = toIngredient.convert(ingredientCommand);
                        ingredient.setRecipe(recipe);
                        recipe.getIngredients().add(ingredient);
            });
        }

        if (!source.getCategories().isEmpty()) {
            source.getCategories()
                    .stream()
                    .forEach(category -> {
                        Category categoryReturn = toCategory.convert(category);
                        categoryReturn.getRecipes().add(recipe);
                        recipe.getCategories().add(categoryReturn);
                    });
        }

        return recipe;
    }
}
