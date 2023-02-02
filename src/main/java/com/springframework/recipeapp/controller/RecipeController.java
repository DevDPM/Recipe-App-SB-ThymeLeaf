package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.exception.BadRequestException;
import com.springframework.recipeapp.exception.NotFoundException;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/{id}/show")
    public String getRecipeByIdPage(@PathVariable Long id, Model model) throws BadRequestException {

        Recipe recipe = recipeService.findById(id);

        model.addAttribute("recipe", recipe);

        return "recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(id);
        model.addAttribute("recipe",recipeCommand);

        return "recipe/recipeform";
    }

    @PostMapping
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand recipeCommandReturn = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + recipeCommandReturn.getId() + "/show";
    }

    @GetMapping("{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {

        log.error("handling not found exception");

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("error/404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception) {

        log.error("handling Numberformat exception");

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("error/400error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
