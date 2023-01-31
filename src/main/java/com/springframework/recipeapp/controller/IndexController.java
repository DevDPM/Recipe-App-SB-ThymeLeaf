package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

        Set<Recipe> recipeList = recipeService.getRecipes().stream().sorted(Comparator.comparing(Recipe::getId)).collect(Collectors.toCollection(LinkedHashSet::new));

        model.addAttribute("recipeList", recipeList);

        return "index";
    }
}
