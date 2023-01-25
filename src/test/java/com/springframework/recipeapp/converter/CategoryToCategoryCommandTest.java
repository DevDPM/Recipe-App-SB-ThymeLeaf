package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.model.Category;
import com.springframework.recipeapp.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    public final Long LONG_ID = 1L;
    public final String DESCRIPTION = "description";
    public final Set<Recipe> RECIPES = new HashSet<>();

    CategoryToCategoryCommand toCategoryCommand;

    @BeforeEach
    void setUp() {
        toCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    void convert() {
        Category category = new Category();
        category.setId(LONG_ID);
        category.setDescription(DESCRIPTION);
        RECIPES.add(new Recipe());

        category.setRecipes(RECIPES);

        assertNotNull(toCategoryCommand.convert(category));
        assertNull(toCategoryCommand.convert(category).getRecipes());
        assertEquals(LONG_ID, toCategoryCommand.convert(category).getId());
        assertEquals(DESCRIPTION, toCategoryCommand.convert(category).getDescription());
    }

    @Test
    void testNullParameter() {
        assertNull(toCategoryCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(toCategoryCommand.convert(new Category()));
    }
}