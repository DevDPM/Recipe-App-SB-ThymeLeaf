package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {


    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/"})
    public String getIndexPage(Model model) {

        model.addAttribute("recipeList", recipeService.getRecipes());

        return "index";
    }
}
