package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand toNotesCommand;
    private final CategoryToCategoryCommand toCategoryCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand toNotesCommand,
                                 CategoryToCategoryCommand toCategoryCommand) {
        this.toNotesCommand = toNotesCommand;
        this.toCategoryCommand = toCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null)
            return null;

        RecipeCommand command = new RecipeCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        command.setDirections(source.getDirections());
        command.setServings(Integer.valueOf(source.getServings()));
        command.setDifficulty(source.getDifficulty());
        command.setUrl(source.getUrl());
        command.setPrepTime(Integer.valueOf(source.getPrepTime()));
        command.setCookTime(Integer.valueOf(source.getCookTime()));
        command.setSource(source.getSource());

        command.setNotes(toNotesCommand.convert(source.getNotes()));

        if (!source.getCategories().isEmpty()) {

            source.getCategories()
                    .stream()
                    .forEach(category -> {
                        command.getCategories()
                                .add(toCategoryCommand.convert(category));
                    });
        }

        return command;
    }
}
