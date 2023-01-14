package com.springframework.recipeapp.controller;

import com.springframework.recipeapp.model.Category;
import com.springframework.recipeapp.model.UnitOfMeasure;
import com.springframework.recipeapp.repositories.CategoryRepository;
import com.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/"})
    public String getIndexPage() {

        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByUnit("Teaspoon");

        System.out.println("Category id of " + categoryOptional.get().getDescription() +
                ": " + categoryOptional.get().getId());

        System.out.println("UnitOfMeasure id of " + unitOfMeasureOptional.get().getUnit() +
                ": " + unitOfMeasureOptional.get().getId());

        return "index";
    }
}
