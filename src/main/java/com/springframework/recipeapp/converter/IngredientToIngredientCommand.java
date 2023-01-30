package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.model.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand) {
        this.toUnitOfMeasureCommand = toUnitOfMeasureCommand;
    }

    @Override
    public IngredientCommand convert(Ingredient source) {

        if (source == null)
            return null;

        IngredientCommand command = new IngredientCommand();
        command.setId(source.getId());

        command.setDescription(source.getDescription());
        command.setAmount(source.getAmount());
        command.setUnitOfMeasure(toUnitOfMeasureCommand.convert(source.getUnitOfMeasure()));

        return command;
    }
}
