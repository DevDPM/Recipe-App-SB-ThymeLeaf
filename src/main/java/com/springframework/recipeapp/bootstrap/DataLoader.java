package com.springframework.recipeapp.bootstrap;

import com.springframework.recipeapp.model.*;
import com.springframework.recipeapp.model.constant.Difficulty;
import com.springframework.recipeapp.repositories.CategoryRepository;
import com.springframework.recipeapp.repositories.RecipeRepository;
import com.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository,
                      RecipeRepository recipeRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("Bootstrap items being added");
        Recipe recipe = new Recipe();

        Set<Category> categoryList = new HashSet<>();

        categoryList.add(categoryRepository.findByDescription("Mexican").get());
        categoryList.add(categoryRepository.findByDescription("Fast Food").get());
        CreateRecipe(recipe,
                "15 min",
                "20 min",
                "4 to 6 servings",
                Difficulty.MEDIUM,
                "Spicy Grilled Chicken Tacos",
                "Directions?",
                categoryList
                );

        addNotes(recipe, "Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");

        addIngredient(recipe,
                2,
                unitOfMeasureRepository.findByUnit("Tablespoon").get(),
                "ancho chili powder");
        addIngredient(recipe,
                1,
                unitOfMeasureRepository.findByUnit("Teaspoon").get(),
                "dried oregano");
        addIngredient(recipe,
                1,
                unitOfMeasureRepository.findByUnit("Teaspoon").get(),
                "dried cumin");
        addIngredient(recipe,
                1,
                unitOfMeasureRepository.findByUnit("Teaspoon").get(),
                "sugar");
        addIngredient(recipe,
                0.5,
                unitOfMeasureRepository.findByUnit("Teaspoon").get(),
                "kosher salt");
        addIngredient(recipe,
                1,
                unitOfMeasureRepository.findByUnit("Clove").get(),
                "garlic, finely chopped");
        addIngredient(recipe,
                1,
                unitOfMeasureRepository.findByUnit("Tablespoon").get(),
                "finely grated orange zest");
        addIngredient(recipe,
                3,
                unitOfMeasureRepository.findByUnit("Tablespoon").get(),
                "fresh-squeezed orange juice");
        addIngredient(recipe,
                2,
                unitOfMeasureRepository.findByUnit("Teaspoon").get(),
                "olive oil");
        addIngredient(recipe,
                5,
                unitOfMeasureRepository.findByUnit("Piece").get(),
                "skinless, boneless chicken tighs");

        recipeRepository.save(recipe);

        Recipe recipe2 = new Recipe();
        Set<Category> categoryList2 = new HashSet<>();

        categoryList2.add(categoryRepository.findByDescription("Italian").get());
        categoryList2.add(categoryRepository.findByDescription("Healthy Food").get());
        CreateRecipe(recipe2,
                "10 min",
                "10 min",
                "2 to 4 servings",
                Difficulty.EASY,
                "the Best Guacamole",
                "Directions?",
                categoryList2
        );

        addNotes(recipe2,
                "Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");

        addIngredient(recipe2,
                2,
                unitOfMeasureRepository.findByUnit("Piece").get(),
                "avocado's");
        addIngredient(recipe2,
                0.25,
                unitOfMeasureRepository.findByUnit("Teaspoon").get(),
                "kosher salt");
        addIngredient(recipe2,
                1,
                unitOfMeasureRepository.findByUnit("Tablespoon").get(),
                "fresh lime or lemon juice");
        addIngredient(recipe2,
                3,
                unitOfMeasureRepository.findByUnit("Tablespoon").get(),
                "minced red onion or thinly slices green onion");
        addIngredient(recipe2,
                2,
                unitOfMeasureRepository.findByUnit("Piece").get(),
                "serrano (or jalapeno) chili's, stems and seeds removed, minced");
        addIngredient(recipe2,
                2,
                unitOfMeasureRepository.findByUnit("Tablespoon").get(),
                "cilantro (leaves and tender stems), finely chopped");
        addIngredient(recipe2,
                1,
                unitOfMeasureRepository.findByUnit("Pinch").get(),
                "freshly ground black pepper");
        addIngredient(recipe2,
                0.5,
                unitOfMeasureRepository.findByUnit("Piece").get(),
                "ripe tomato, chopped (optional)");
        addIngredient(recipe2,
                1,
                unitOfMeasureRepository.findByUnit("Bag").get(),
                "tortilla chips (to serve)");

        recipeRepository.save(recipe2);
        categoryList.clear();

        log.debug("Bootstrap items added & saved");

    }
    private void CreateRecipe(Recipe recipe, String cookTime, String prepTime, String servings, Difficulty difficulty,
                              String description, String directions, Set<Category> categories) {
        recipe.setCookTime(cookTime);
        recipe.setPrepTime(prepTime);
        recipe.setServings(servings);
        recipe.setDifficulty(difficulty);
        recipe.setDescription(description);
        recipe.setDirections(directions);
        recipe.setCategories(categories);
    }

    private void addNotes(Recipe recipe, String description) {
        Notes notes = new Notes();
        notes.setRecipe(recipe);
        notes.setRecipeNotes(description);
        recipe.setNotes(notes);
    }

    private void addIngredient(Recipe recipe, double amount, UnitOfMeasure unit, String ingredientName) {
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription(ingredientName);
        ingredient.setUnitOfMeasure(unit);
        ingredient.setAmount(new BigDecimal(amount));
        ingredient.setRecipe(recipe);
        recipe.getIngredients().add(ingredient);
    }
}
