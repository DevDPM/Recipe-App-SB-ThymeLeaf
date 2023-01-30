package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.command.IngredientCommand;
import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.command.UnitOfMeasureCommand;
import com.springframework.recipeapp.service.IngredientService;
import com.springframework.recipeapp.service.RecipeService;
import com.springframework.recipeapp.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

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

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(recipeCommand.getId());
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);

        model.addAttribute("uomList", unitOfMeasureService.listOfAllUoM());

        return "recipe/ingredient/show";
    }

    @PostMapping("/{recipeId}/ingredient/add")
    public String saveOrUpdate(@RequestParam String uomID, @PathVariable String recipeId, @ModelAttribute IngredientCommand ingredientCommand) {
        RecipeCommand recipeCommandReturn = recipeService.findCommandById(Long.valueOf(recipeId));

        UnitOfMeasureCommand uomCommand = unitOfMeasureService.findById(Long.valueOf(uomID));
        ingredientCommand.setUnitOfMeasure(uomCommand);
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));

        recipeCommandReturn.getIngredients().add(ingredientCommand);
        RecipeCommand saveRecipeCommand = recipeService.saveRecipeCommand(recipeCommandReturn);

        return "redirect:/recipe/" + saveRecipeCommand.getId() + "/ingredients/show";
    }

    @PostMapping("/{recipeId}/ingredient/{id}/update")
    public String updateById(@RequestParam String uomID,
                             @RequestParam String description,
                             @RequestParam String amount,
                             @PathVariable String recipeId, @PathVariable String id, @ModelAttribute IngredientCommand ingredientCommand) {

        UnitOfMeasureCommand uomCommand = unitOfMeasureService.findById(Long.valueOf(uomID));
        ingredientCommand.setUnitOfMeasure(uomCommand);
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        ingredientCommand.setAmount(BigDecimal.valueOf(Double.parseDouble(amount)));
        ingredientCommand.setDescription(description);

        System.out.println(ingredientCommand.getId());
        System.out.println(ingredientCommand.getDescription());
        System.out.println(ingredientCommand.getRecipeId());
        System.out.println(ingredientCommand.getAmount());
        System.out.println(ingredientCommand.getUnitOfMeasure());
        System.out.println(ingredientCommand.getRecipe());

        IngredientCommand ingredientCommandReturn = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id));



        IngredientCommand saveIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommandReturn);


        return "redirect:/recipe/" + saveIngredientCommand.getRecipe().getId() + "/ingredients/show";
    }
}
