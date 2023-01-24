package com.springframework.recipeapp.converter;

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

    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter,
                                 IngredientCommandToIngredient ingredientConverter,
                                 NotesCommandToNotes notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null)
            return null;

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());

        Set<Ingredient> ingredients = new HashSet<>();
        if (!source.getIngredients().isEmpty()) {
            source.getIngredients()
                    .stream()
                    .forEach(ingredientCommand -> {
                        Ingredient ingredient = ingredientConverter.convert(ingredientCommand);
                        ingredient.setRecipe(recipe);
                        ingredients.add(ingredient);
            });
        }
        recipe.setIngredients(ingredients);

        if (notesConverter.convert(source.getNotes()) != null) {
            Notes notes = notesConverter.convert(source.getNotes());
            notes.setRecipe(recipe);
            recipe.setNotes(notes);
        }

        Set<Category> categories = new HashSet<>();
        if (!source.getCategories().isEmpty()) {
            source.getCategories()
                    .stream()
                    .forEach(category -> {
                        categories.add(categoryConverter.convert(category));
                    });
        }
        recipe.setCategories(categories);

        recipe.setDifficulty(source.getDifficulty());
        recipe.setDescription(source.getDescription());
        recipe.setDirections(source.getDirections());
        recipe.setServings(String.valueOf(source.getServings()));
        recipe.setPrepTime(String.valueOf(source.getPrepTime()));
        recipe.setCookTime(String.valueOf(source.getCookTime()));

        return recipe;
    }
}
