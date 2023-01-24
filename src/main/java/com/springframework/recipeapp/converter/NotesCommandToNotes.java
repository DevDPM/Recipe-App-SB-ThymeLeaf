package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.NotesCommand;
import com.springframework.recipeapp.model.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if (source == null)
            return null;

        final Notes notes = new Notes();
        notes.setRecipe(source.getRecipe());
        notes.setRecipeNotes(source.getRecipeNotes());
        notes.setId(source.getId());

        return notes;
    }
}
