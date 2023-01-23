package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/show/{id}")
    public String getRecipeByIdPage(@PathVariable String id, Model model) {

        Recipe recipe = recipeService.getRecipeById(Long.parseLong(id));

        model.addAttribute("recipe", recipe);

        return "recipes/show";
    }
}
