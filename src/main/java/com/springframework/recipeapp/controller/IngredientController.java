package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.command.UnitOfMeasureCommand;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.service.IngredientService;
import com.springframework.recipeapp.service.RecipeService;
import com.springframework.recipeapp.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/recipe")
public class IngredientController {

    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final IngredientService ingredientService;


    public IngredientController(RecipeService recipeService,
                                UnitOfMeasureService unitOfMeasureService,
                                IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("{id}/ingredients/show")
    public String getIngredientByRecipeId(@PathVariable String id, Model model) {

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.parseLong(id));
        model.addAttribute("recipe", recipeCommand);

        // Capitalize first letter in description
        Set<IngredientCommand> ingredientCommandSetFormatted = recipeCommand.getIngredients()
                .stream()
                .map(ingredientCommand -> {
                    String ingredient = ingredientCommand.getDescription();
                    ingredientCommand.setDescription(ingredient.substring(0, 1).toUpperCase() + ingredient.substring(1).toLowerCase());
                    return ingredientCommand;
                })
                .collect(Collectors.toSet());

        model.addAttribute("sortedIngredient", ingredientCommandSetFormatted
                        .stream()
                        .sorted(Comparator.comparing(IngredientCommand::getDescription)));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipe(recipeCommand);
        ingredientCommand.setRecipeId(recipeCommand.getId());
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);

        model.addAttribute("uomList", unitOfMeasureService.listOfAllUoM());

        return "recipe/ingredient/show";
    }

    @PostMapping
    @RequestMapping("/{recipeId}/ingredient/add")
    public String saveOrUpdate(@RequestParam String uomID, @PathVariable String recipeId, @ModelAttribute IngredientCommand ingredientCommand) {
        RecipeCommand recipeCommandReturn = recipeService.findCommandById(Long.valueOf(recipeId));

        UnitOfMeasureCommand uomCommand = unitOfMeasureService.findById(Long.valueOf(uomID));
        ingredientCommand.setUnitOfMeasure(uomCommand);
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        ingredientCommand.setRecipe(recipeCommandReturn);

        recipeCommandReturn.getIngredients().add(ingredientCommand);
        RecipeCommand saveRecipeCommand = recipeService.saveRecipeCommand(recipeCommandReturn);

        return "redirect:/recipe/" + saveRecipeCommand.getId() + "/ingredients/show";
    }

    @PostMapping
    @RequestMapping("/{recipeId}/ingredient/{id}/update")
    public String updateById(@RequestParam String uomID,
                             @RequestParam String ingredientDescription,
                             @RequestParam String amount,
                             @PathVariable String recipeId, @PathVariable String id) {

        IngredientCommand ingredientCommandReturn = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id));

        UnitOfMeasureCommand uomCommand = unitOfMeasureService.findById(Long.valueOf(uomID));
        ingredientCommandReturn.setUnitOfMeasure(uomCommand);
        ingredientCommandReturn.setRecipeId(Long.valueOf(recipeId));
        ingredientCommandReturn.setAmount(BigDecimal.valueOf(Double.parseDouble(amount)));
        ingredientCommandReturn.setDescription(ingredientDescription);

        Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
        RecipeCommand recipeCommand = recipeService.toRecipeCommand(recipe);
        ingredientCommandReturn.setRecipe(recipeCommand);

        IngredientCommand saveIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommandReturn);
        
        saveIngredientCommand.setRecipe(recipeCommand);

        return "redirect:/recipe/" + saveIngredientCommand.getRecipe().getId() + "/ingredients/show";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{id}/delete")
    public String deleteById(@PathVariable String recipeId, @PathVariable String id) {

        final Long LONG_RECIPE_ID = Long.valueOf(recipeId);
        final Long LONG_INGREDIENT_ID = Long.valueOf(id);

        IngredientCommand ingredientCommandReturn = ingredientService.findByRecipeIdAndIngredientId(LONG_RECIPE_ID, LONG_INGREDIENT_ID);
        RecipeCommand recipeCommand = recipeService.findCommandById(LONG_RECIPE_ID);

        RecipeCommand updatedRecipeCommand = ingredientService.deleteById(recipeCommand.getId(), ingredientCommandReturn.getId());

        return "redirect:/recipe/" + updatedRecipeCommand.getId() + "/ingredients/show";
    }
}
