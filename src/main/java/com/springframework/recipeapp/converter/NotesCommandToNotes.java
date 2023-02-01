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
        System.out.println("NotesCommandToNotes source.getID: " + source.getId());
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());

        return notes;
    }
}
