package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.NotesCommand;
import com.springframework.recipeapp.model.Notes;
import com.springframework.recipeapp.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    NotesToNotesCommand toNotesCommand;

    public final String RECIPE_NOTES = "recipe notes";
    public final Long LONG_ID = 1L;
    public final Recipe RECIPE = new Recipe();


    @BeforeEach
    void setUp() {
        toNotesCommand = new NotesToNotesCommand();
    }

    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setRecipeNotes(RECIPE_NOTES);
        notes.setId(LONG_ID);
        notes.setRecipe(RECIPE);

        NotesCommand notesCommandReturn = toNotesCommand.convert(notes);

        assertEquals(notes.getRecipeNotes(), notesCommandReturn.getRecipeNotes());
        assertEquals(notes.getId(), notesCommandReturn.getId());
        assertNull(notesCommandReturn.getRecipe());
    }

    @Test
    void testNullParameter() {
        assertNull(toNotesCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(toNotesCommand.convert(new Notes()));
    }
}