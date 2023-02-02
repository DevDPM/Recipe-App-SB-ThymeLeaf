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
    public String getIngredientByRecipeId(@PathVariable Long id,
                                          Model model) {

        RecipeCommand recipeCommand = recipeService.findCommandById(id);
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
    public String saveOrUpdate(@RequestParam Long uomID,
                               @PathVariable Long recipeId,
                               @ModelAttribute IngredientCommand ingredientCommand) {
        RecipeCommand recipeCommandReturn = recipeService.findCommandById(recipeId);

        UnitOfMeasureCommand uomCommand = unitOfMeasureService.findById(uomID);
        ingredientCommand.setUnitOfMeasure(uomCommand);
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setRecipe(recipeCommandReturn);

        recipeCommandReturn.getIngredients().add(ingredientCommand);
        RecipeCommand saveRecipeCommand = recipeService.saveRecipeCommand(recipeCommandReturn);

        return "redirect:/recipe/" + saveRecipeCommand.getId() + "/ingredients/show";
    }

    @PostMapping
    @RequestMapping("/{recipeId}/ingredient/{id}/update")
    public String updateById(@RequestParam Long uomID,
                             @RequestParam String ingredientDescription,
                             @RequestParam BigDecimal amount,
                             @PathVariable Long recipeId, @PathVariable Long id) {

        IngredientCommand ingredientCommandReturn = ingredientService.findByRecipeIdAndIngredientId(recipeId, id);

        UnitOfMeasureCommand uomCommand = unitOfMeasureService.findById(uomID);
        ingredientCommandReturn.setUnitOfMeasure(uomCommand);
        ingredientCommandReturn.setRecipeId(recipeId);
        ingredientCommandReturn.setAmount(amount);
        ingredientCommandReturn.setDescription(ingredientDescription);

        Recipe recipe = recipeService.findById(recipeId);
        RecipeCommand recipeCommand = recipeService.toRecipeCommand(recipe);
        ingredientCommandReturn.setRecipe(recipeCommand);

        IngredientCommand saveIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommandReturn);
        
        saveIngredientCommand.setRecipe(recipeCommand);

        return "redirect:/recipe/" + saveIngredientCommand.getRecipe().getId() + "/ingredients/show";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{id}/delete")
    public String deleteById(@PathVariable Long recipeId,
                             @PathVariable Long id) {

        IngredientCommand ingredientCommandReturn = ingredientService.findByRecipeIdAndIngredientId(recipeId, id);
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);

        RecipeCommand updatedRecipeCommand = ingredientService.deleteById(recipeCommand.getId(), ingredientCommandReturn.getId());

        return "redirect:/recipe/" + updatedRecipeCommand.getId() + "/ingredients/show";
    }
}
