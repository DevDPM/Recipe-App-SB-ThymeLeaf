package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.model.Ingredient;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    public IngredientToIngredientCommand toIngredientCommand;
    public UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    public final Long LONG_ID = 1L;
    public final String DESCRIPTION = "description";
    public final Recipe RECIPE = new Recipe();
    public final UnitOfMeasure UOM = new UnitOfMeasure();
    public final BigDecimal AMOUNT = new BigDecimal(10.0);

    @BeforeEach
    void setUp() {
        toUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        toIngredientCommand = new IngredientToIngredientCommand(toUnitOfMeasureCommand);
    }

    @Test
    void convert() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(LONG_ID);
        UOM.setUnit("test unit");
        UOM.setId(LONG_ID);
        ingredient.setUnitOfMeasure(UOM);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setRecipe(RECIPE);

        IngredientCommand ingredientReturn = toIngredientCommand.convert(ingredient);

        assertEquals(ingredient.getId(),ingredientReturn.getId());
        assertEquals(ingredient.getAmount(),ingredientReturn.getAmount());
        assertEquals(ingredient.getDescription(),ingredientReturn.getDescription());
        assertEquals(ingredient.getUnitOfMeasure().getUnit(),ingredientReturn.getUnitOfMeasure().getUnit());
        assertEquals(ingredient.getUnitOfMeasure().getId(),ingredientReturn.getUnitOfMeasure().getId());

        assertNull(ingredientReturn.getRecipe());
    }

    @Test
    void testNullParameter() {
        assertNull(toIngredientCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(toIngredientCommand.convert(new Ingredient()));
    }
}