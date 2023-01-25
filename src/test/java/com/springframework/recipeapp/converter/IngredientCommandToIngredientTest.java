package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.command.UnitOfMeasureCommand;
import com.springframework.recipeapp.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    public IngredientCommandToIngredient toIngredient;
    public UnitOfMeasureCommandToUnitOfMeasure toUnitOfMeasure;

    public final Long LONG_ID = 1L;
    public final String DESCRIPTION = "description";
    public final RecipeCommand RECIPE = new RecipeCommand();
    public final UnitOfMeasureCommand UOM = new UnitOfMeasureCommand();
    public final BigDecimal AMOUNT = new BigDecimal(10.0);


    @BeforeEach
    void setUp() {
        toUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        toIngredient = new IngredientCommandToIngredient(toUnitOfMeasure);
    }

    @Test
    void convert() {
        IngredientCommand command = new IngredientCommand();
        command.setDescription(DESCRIPTION);
        command.setRecipe(RECIPE);
        UOM.setUnit("test");
        UOM.setId(5L);
        command.setUnitOfMeasure(UOM);
        command.setAmount(AMOUNT);
        command.setId(LONG_ID);

        Ingredient ingredientReturn = toIngredient.convert(command);

        assertEquals(command.getId(), ingredientReturn.getId());
        assertEquals(command.getDescription(), ingredientReturn.getDescription());
        assertEquals(command.getAmount(), ingredientReturn.getAmount());
        assertEquals(command.getUnitOfMeasure().getUnit(), ingredientReturn.getUnitOfMeasure().getUnit());
        assertEquals(command.getUnitOfMeasure().getId(), ingredientReturn.getUnitOfMeasure().getId());
        assertNull(ingredientReturn.getRecipe());
    }

    @Test
    void testNullParameter() {
        assertNull(toIngredient.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(toIngredient.convert(new IngredientCommand()));
    }

}