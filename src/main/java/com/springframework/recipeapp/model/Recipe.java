package com.springframework.recipeapp.model;

public class Recipe {
    private String recipeName;
    private String ingredients;

    public Recipe(String recipeName, String ingredients) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
