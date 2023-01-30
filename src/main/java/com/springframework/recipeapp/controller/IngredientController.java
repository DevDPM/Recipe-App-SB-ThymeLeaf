package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.command.UnitOfMeasureCommand;
import com.springframework.recipeapp.model.UnitOfMeasure;
import com.springframework.recipeapp.service.RecipeService;
import com.springframework.recipeapp.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/recipe")
public class IngredientController {

    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("{id}/ingredients/show")
    public String getIngredientByRecipeId(@PathVariable String id, Model model) {

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.parseLong(id));
        model.addAttribute("recipe", recipeCommand);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(recipeCommand.getId());
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);

        model.addAttribute("uomList", unitOfMeasureService.listOfAllUoM());

        return "recipe/ingredient/show";
    }

    @PostMapping("/{recipeId}/ingredient/add")
    public String saveOrUpdate(@RequestParam String uomID, @PathVariable String recipeId, @ModelAttribute IngredientCommand ingredientCommand) {
        System.out.println(uomID);
        RecipeCommand recipeCommandReturn = recipeService.findCommandById(Long.valueOf(recipeId));

        UnitOfMeasureCommand uomCommand = unitOfMeasureService.findById(Long.valueOf(uomID));
        ingredientCommand.setUnitOfMeasure(uomCommand);

        recipeCommandReturn.getIngredients().add(ingredientCommand);
        RecipeCommand saveRecipeCommand = recipeService.saveRecipeCommand(recipeCommandReturn);

        return "redirect:/recipe/" + saveRecipeCommand.getId() + "/ingredients/show";
    }
}
