package com.springframework.recipeapp.repository;

import com.springframework.recipeapp.command.UnitOfMeasureCommand;
import com.springframework.recipeapp.model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByUnit(String unit);
}
