package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure toUnitOfMeasure;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure toUnitOfMeasure) {
        this.toUnitOfMeasure = toUnitOfMeasure;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null)
            return null;

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setDescription(source.getDescription());

        if (toUnitOfMeasure.convert(source.getUnitOfMeasure()) != null) {
            ingredient.setUnitOfMeasure(toUnitOfMeasure.convert(source.getUnitOfMeasure()));
        }

        ingredient.setAmount(source.getAmount());

        return ingredient;
    }
}
