package com.springframework.recipeapp.command;

import com.springframework.recipeapp.model.Category;
import com.springframework.recipeapp.model.Ingredient;
import com.springframework.recipeapp.model.Notes;
import com.springframework.recipeapp.model.constant.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Notes notes;
    private Difficulty difficulty;
    private Set<Ingredient> ingredients = new HashSet<>();
    private Set<Category> categories = new HashSet<>();
    private Byte[] image;

}
