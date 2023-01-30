package com.springframework.recipeapp.service;

import com.springframework.recipeapp.command.UnitOfMeasureCommand;
import com.springframework.recipeapp.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.recipeapp.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.toUnitOfMeasureCommand = toUnitOfMeasureCommand;
    }

    @Override
    public UnitOfMeasureCommand findById(Long id) {
        return toUnitOfMeasureCommand.convert(unitOfMeasureRepository.findById(id).orElse(null));
    }

    @Override
    public List<UnitOfMeasureCommand> listOfAllUoM() {

        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(e -> toUnitOfMeasureCommand.convert(e))
                .collect(Collectors.toList());
    }
}
