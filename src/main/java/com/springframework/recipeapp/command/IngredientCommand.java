package com.springframework.recipeapp.command;

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
    private Long recipeId;
    private RecipeCommand recipe;
    private UnitOfMeasureCommand unitOfMeasure;

    @Override
    public String toString() {
        return "IngredientCommand{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", recipeId=" + recipeId +
                ", recipe=" + recipe +
                ", unitOfMeasure=" + unitOfMeasure +
                '}';
    }
}
