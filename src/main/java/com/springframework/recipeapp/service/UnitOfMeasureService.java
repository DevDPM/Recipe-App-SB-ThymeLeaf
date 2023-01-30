package com.springframework.recipeapp.service;

import com.springframework.recipeapp.command.UnitOfMeasureCommand;

import java.util.List;

public interface UnitOfMeasureService {

    List<UnitOfMeasureCommand> listOfAllUoM();

    UnitOfMeasureCommand findById(Long id);
}
