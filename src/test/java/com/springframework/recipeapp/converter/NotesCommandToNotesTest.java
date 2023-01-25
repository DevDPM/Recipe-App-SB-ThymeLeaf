package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.NotesCommand;
import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.model.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    NotesCommandToNotes toNotes;

    public final String RECIPE_NOTES = "recipe notes";
    public final Long LONG_ID = 1L;
    public final RecipeCommand RECIPE = new RecipeCommand();

    @BeforeEach
    void setUp() {
        toNotes = new NotesCommandToNotes();
    }

    @Test
    void convert() {
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setRecipeNotes(RECIPE_NOTES);
        notesCommand.setId(LONG_ID);
        notesCommand.setRecipe(RECIPE);

        Notes notes = toNotes.convert(notesCommand);

        assertEquals(notesCommand.getRecipeNotes(), notes.getRecipeNotes());
        assertEquals(notesCommand.getId(), notes.getId());
        assertNull(notes.getRecipe());
    }

    @Test
    void testNullParameter() {
        assertNull(toNotes.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(toNotes.convert(new NotesCommand()));
    }
}