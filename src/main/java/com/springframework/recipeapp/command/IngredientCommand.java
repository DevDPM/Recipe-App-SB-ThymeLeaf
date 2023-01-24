package com.springframework.recipeapp.command;

import com.springframework.recipeapp.model.Recipe;
import com.springframework.recipeapp.model.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private Long id;
    private BigDecimal amount;
    private String description;
    private Recipe recipe;
    private UnitOfMeasure unitOfMeasure;

}
