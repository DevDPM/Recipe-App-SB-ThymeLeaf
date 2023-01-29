package com.springframework.recipeapp.service;

import com.springframework.recipeapp.command.RecipeCommand;
import com.springframework.recipeapp.converter.RecipeCommandToRecipe;
import com.springframework.recipeapp.converter.RecipeToRecipeCommand;
import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand toRecipeCommand;
    private final RecipeCommandToRecipe toRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeToRecipeCommand toRecipeCommand,
                             RecipeCommandToRecipe toRecipe) {
        this.recipeRepository = recipeRepository;
        this.toRecipeCommand = toRecipeCommand;
        this.toRecipe = toRecipe;
    }

    @Override
    public Set<Recipe> getRecipes() {

        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipe -> { recipeSet.add(recipe); });
        return recipeSet;
    }

    @Override
    public Recipe getRecipeById(Long id) {
//        return Optional.ofNullable(recipeRepository.findById(id)).get().get(); // can be more sophisticated
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found!"));
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return toRecipeCommand.convert(getRecipeById(id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        if (recipeCommand == null)
            return null;

        Recipe recipeReturn = toRecipe.convert(recipeCommand);
        Recipe recipeSaved = recipeRepository.save(recipeReturn);
        RecipeCommand recipeCommandReturn = toRecipeCommand.convert(recipeSaved);

        return recipeCommandReturn;
    }

    @Override
    public void deleteRecipe(RecipeCommand recipeCommand) {
        if (recipeCommand == null)
            return;

        Recipe recipeReturn = toRecipe.convert(recipeCommand);
        if (recipeReturn == null)
            return;

        recipeRepository.delete(recipeReturn);

    }
}
