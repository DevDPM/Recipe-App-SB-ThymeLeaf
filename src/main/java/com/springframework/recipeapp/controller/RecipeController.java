package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/{id}/show")
    public String getRecipeByIdPage(@PathVariable String id, Model model) {

        Recipe recipe = recipeService.getRecipeById(Long.parseLong(id));

        model.addAttribute("recipe", recipe);

        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
        model.addAttribute("recipe",recipeCommand);

        return "recipe/recipeform";
    }

    @PostMapping
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand recipeCommandReturn = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + recipeCommandReturn.getId() + "/show";
    }

    @GetMapping("{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipeById(Long.valueOf(id));

        return "redirect:/";
    }
}
