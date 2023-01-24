package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null)
            return null;

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setIngredients(source.getIngredients());
        recipe.setNotes(source.getNotes());
        recipe.setCategories(source.getCategories());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDescription(source.getDescription());
        recipe.setDirections(source.getDirections());
        recipe.setServings(String.valueOf(source.getServings()));
        recipe.setPrepTime(String.valueOf(source.getPrepTime()));
        recipe.setCookTime(String.valueOf(source.getCookTime()));
        recipe.setImage(source.getImage());

        return recipe;
    }
}
