package com.springframework.recipeapp.command;

import com.springframework.recipeapp.model.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {

    private Long id;
    private String description;
    private Set<Recipe> recipes;
}
