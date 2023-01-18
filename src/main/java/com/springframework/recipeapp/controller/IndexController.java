package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {


    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/"})
    public String getIndexPage(Model model) {
        log.debug("IndexPage requested");

        model.addAttribute("recipeList", recipeService.getRecipes());

        return "index";
    }
}
