package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.CategoryCommand;
import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.model.*;
import com.springframework.recipeapp.model.constant.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

    public RecipeToRecipeCommand toRecipeCommand;
    public CategoryToCategoryCommand toCategoryCommand;
    public IngredientToIngredientCommand toIngredientCommand;
    public NotesToNotesCommand toNotesCommand;
    public static final Category category = new Category();
    public static final UnitOfMeasure uom = new UnitOfMeasure();
    public static final Ingredient ingredient = new Ingredient();
    public static final Notes notes = new Notes();
    public static final Recipe recipe = new Recipe();

    public static final Long LONG_ID_CATEGORY = 1L;
    public static final Long LONG_ID_UOM = 2L;
    public static final Long LONG_ID_INGREDIENT = 3L;
    public static final Long LONG_ID_RECIPE = 4L;
    public static final Long LONG_ID_NOTES = 5L;
    public static final String DESCRIPTION = "description";
    public static final String UNIT = "unit";
    public static final BigDecimal AMOUNT = new BigDecimal(10);
    public static final String RECIPE_NOTES = "recipe_notes";
    public static final String STRING_INTEGER_VALUE = "20";
    public static final String URL = "url";
    public static final String SOURCE = "source";
    private static final String DIRECTIONS = "directions";

    @BeforeEach
    void setUp() {
        toCategoryCommand = new CategoryToCategoryCommand();
        toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand(), toRecipeCommand);
        toNotesCommand = new NotesToNotesCommand();
        toRecipeCommand = new RecipeToRecipeCommand(toNotesCommand, toIngredientCommand, toCategoryCommand);
    }

    @Test
    void convert() {

        category.setId(LONG_ID_CATEGORY);
        category.setDescription(DESCRIPTION);

        uom.setId(LONG_ID_UOM);
        uom.setUnit(UNIT);

        ingredient.setId(LONG_ID_INGREDIENT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setUnitOfMeasure(uom);

        notes.setId(LONG_ID_NOTES);
        notes.setRecipeNotes(RECIPE_NOTES);

        recipe.setId(LONG_ID_RECIPE);
        recipe.setDirections(DIRECTIONS);
        recipe.setCookTime(STRING_INTEGER_VALUE);
        recipe.setPrepTime(STRING_INTEGER_VALUE);
        recipe.setDifficulty(Difficulty.HARD);
        recipe.setServings(STRING_INTEGER_VALUE);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDescription(DESCRIPTION);

        Set<Category> categories = new HashSet<>();
        categories.add(category);
        recipe.setCategories(categories);
        recipe.getIngredients().add(ingredient);
        recipe.setNotes(notes);

        RecipeCommand recipeCommandReturn = toRecipeCommand.convert(recipe);


        // Testing for insertion of ingredient & unitOfMeasure to recipe
        Set<IngredientCommand> ingredientCommands = recipeCommandReturn.getIngredients();
        List<IngredientCommand> ingredientList = new ArrayList<>(ingredientCommands); // Convert Set to List to promote .get function

        assertEquals(1,recipeCommandReturn.getIngredients().size());
        assertEquals(uom.getId(), ingredientList.get(0).getUnitOfMeasure().getId());
        assertEquals(uom.getUnit(), ingredientList.get(0).getUnitOfMeasure().getUnit());
        assertEquals(ingredient.getId(), ingredientList.get(0).getId());
        assertEquals(ingredient.getAmount(), ingredientList.get(0).getAmount());
        assertEquals(ingredient.getDescription(), ingredientList.get(0).getDescription());
        assertNull(ingredient.getRecipe());
        assertNotNull(ingredientList.get(0).getRecipe());

        // Testing for insertion of category to recipe
        Set<CategoryCommand> categoryCommands = recipeCommandReturn.getCategories();
        List<CategoryCommand> categoryList = new ArrayList<>(categoryCommands); // Convert Set to List to promote .get function

        assertEquals(1,recipeCommandReturn.getIngredients().size());
        assertEquals(category.getId(),categoryList.get(0).getId());
        assertEquals(category.getDescription(),categoryList.get(0).getDescription());
        assertNotNull(categoryList.get(0).getRecipes());

        // Testing for insertion of notes to recipe
        assertEquals(notes.getId(), recipeCommandReturn.getNotes().getId());
        assertEquals(notes.getRecipeNotes(), recipeCommandReturn.getNotes().getRecipeNotes());
        assertNotNull(recipeCommandReturn.getNotes().getRecipe());
//        assertNull(notes.getRecipe());                 // Can't do this due to stackoverflow

        assertEquals(recipe.getId(),recipeCommandReturn.getId());
        assertEquals(Integer.valueOf(STRING_INTEGER_VALUE),recipeCommandReturn.getCookTime());
        assertEquals(Integer.valueOf(STRING_INTEGER_VALUE),recipeCommandReturn.getServings());
        assertEquals(recipe.getDirections(),recipeCommandReturn.getDirections());
        assertEquals(recipe.getDifficulty(),recipeCommandReturn.getDifficulty());
        assertEquals(Integer.valueOf(STRING_INTEGER_VALUE),recipeCommandReturn.getPrepTime());
        assertEquals(recipe.getSource(),recipeCommandReturn.getSource());
        assertEquals(recipe.getDescription(),recipeCommandReturn.getDescription());
        assertEquals(recipe.getUrl(),recipeCommandReturn.getUrl());
    }

    @Test
    void testNullParameter() {
        assertNull(toRecipeCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(toRecipeCommand.convert(new Recipe()));
    }
}