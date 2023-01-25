package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.CategoryCommand;
import com.springframework.recipeapp.command.RecipeCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    public final Long LONG_ID = 1L;
    public final String DESCRIPTION = "description";
    public final Set<RecipeCommand> RECIPES = new HashSet<>();

    CategoryCommandToCategory toCategory;

    @BeforeEach
    void setUp() {
        toCategory = new CategoryCommandToCategory();
    }

    @Test
    void convert() {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(LONG_ID);
        categoryCommand.setDescription(DESCRIPTION);
        RECIPES.add(new RecipeCommand());

        categoryCommand.setRecipes(RECIPES);

        assertNotNull(toCategory.convert(categoryCommand));
        assertNull(toCategory.convert(categoryCommand).getRecipes());
        assertEquals(LONG_ID, toCategory.convert(categoryCommand).getId());
        assertEquals(DESCRIPTION, toCategory.convert(categoryCommand).getDescription());
    }

    @Test
    void testNullParameter() {
        assertNull(toCategory.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(toCategory.convert(new CategoryCommand()));
    }
}