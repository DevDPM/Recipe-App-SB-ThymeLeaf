package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.*;
import com.springframework.recipeapp.model.Category;
import com.springframework.recipeapp.model.Ingredient;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.model.constant.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    public RecipeCommandToRecipe toRecipe;
    public CategoryCommandToCategory toCategory;
    public IngredientCommandToIngredient toIngredient;
    public NotesCommandToNotes toNotes;
    public static final CategoryCommand categoryCommand = new CategoryCommand();
    public static final UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
    public static final IngredientCommand ingredientCommand = new IngredientCommand();
    public static final NotesCommand notesCommand = new NotesCommand();
    public static final RecipeCommand recipeCommand = new RecipeCommand();

    public static final Long LONG_ID_CATEGORY = 1L;
    public static final Long LONG_ID_UOM = 2L;
    public static final Long LONG_ID_INGREDIENT = 3L;
    public static final Long LONG_ID_RECIPE = 4L;
    public static final Long LONG_ID_NOTES = 5L;
    public static final String DESCRIPTION = "description";
    public static final String UNIT = "unit";
    public static final BigDecimal AMOUNT = new BigDecimal(10);
    public static final String RECIPE_NOTES = "recipe_notes";
    public static final Integer INTEGER_VALUE = 20;
    public static final String URL = "url";
    public static final String SOURCE = "source";
    private static final String DIRECTIONS = "directions";


    @BeforeEach
    void setUp() {
        toCategory = new CategoryCommandToCategory();
        toIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        toNotes = new NotesCommandToNotes();
        toRecipe = new RecipeCommandToRecipe(toCategory, toIngredient, toNotes);
    }

    @Test
    void convert() {

        categoryCommand.setId(LONG_ID_CATEGORY);
        categoryCommand.setDescription(DESCRIPTION);

        uomCommand.setId(LONG_ID_UOM);
        uomCommand.setUnit(UNIT);

        ingredientCommand.setId(LONG_ID_INGREDIENT);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setUnitOfMeasure(uomCommand);

        notesCommand.setId(LONG_ID_NOTES);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        recipeCommand.setId(LONG_ID_RECIPE);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setCookTime(INTEGER_VALUE);
        recipeCommand.setPrepTime(INTEGER_VALUE);
        recipeCommand.setDifficulty(Difficulty.HARD);
        recipeCommand.setServings(INTEGER_VALUE);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDescription(DESCRIPTION);

        recipeCommand.getCategories().add(categoryCommand);
        recipeCommand.getIngredients().add(ingredientCommand);
        recipeCommand.setNotes(notesCommand);

        Recipe recipeReturn = toRecipe.convert(recipeCommand);


        // Testing for insertion of ingredient & unitOfMeasure to recipe
        Set<Ingredient> ingredients = recipeReturn.getIngredients();
        List<Ingredient> ingredientList = new ArrayList<>(ingredients); // Convert Set to List to promote .get function

        assertEquals(1,recipeReturn.getIngredients().size());
        assertEquals(uomCommand.getId(), ingredientList.get(0).getUnitOfMeasure().getId());
        assertEquals(uomCommand.getUnit(), ingredientList.get(0).getUnitOfMeasure().getUnit());
        assertEquals(ingredientCommand.getId(), ingredientList.get(0).getId());
        assertEquals(ingredientCommand.getAmount(), ingredientList.get(0).getAmount());
        assertEquals(ingredientCommand.getDescription(), ingredientList.get(0).getDescription());
        assertNull(ingredientCommand.getRecipe());
        assertNotNull(ingredientList.get(0).getRecipe());

        // Testing for insertion of category to recipe
        Set<Category> categories = recipeReturn.getCategories();
        List<Category> categoryList = new ArrayList<>(categories); // Convert Set to List to promote .get function

        assertEquals(1,recipeReturn.getIngredients().size());
        assertEquals(categoryCommand.getId(),categoryList.get(0).getId());
        assertEquals(categoryCommand.getDescription(),categoryList.get(0).getDescription());
        assertNotNull(categoryList.get(0).getRecipes());

        // Testing for insertion of notes to recipe
        assertEquals(notesCommand.getId(), recipeReturn.getNotes().getId());
        assertEquals(notesCommand.getRecipeNotes(), recipeReturn.getNotes().getRecipeNotes());
        assertNotNull(recipeReturn.getNotes().getRecipe());
        assertNull(notesCommand.getRecipe());

        assertEquals(recipeCommand.getId(),recipeReturn.getId());
        assertEquals(recipeCommand.getCookTime().toString(),recipeReturn.getCookTime());
        assertEquals(recipeCommand.getServings().toString(),recipeReturn.getServings());
        assertEquals(recipeCommand.getDirections(),recipeReturn.getDirections());
        assertEquals(recipeCommand.getDifficulty(),recipeReturn.getDifficulty());
        assertEquals(recipeCommand.getPrepTime().toString(),recipeReturn.getPrepTime());
        assertEquals(recipeCommand.getSource(),recipeReturn.getSource());
        assertEquals(recipeCommand.getDescription(),recipeReturn.getDescription());
        assertEquals(recipeCommand.getUrl(),recipeReturn.getUrl());
    }

    @Test
    void testNullParameter() {
        assertNull(toRecipe.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(toRecipe.convert(new RecipeCommand()));
    }
}