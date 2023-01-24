package com.springframework.recipeapp.repository;

import com.springframework.recipeapp.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;



    @BeforeEach
    void setUp() {

    }

    @Test
    public void findByDescription() throws Exception {

        // given
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByUnit("Teaspoon");

        // then
        assertEquals("Teaspoon", unitOfMeasureOptional.get().getUnit());
    }

    @Test
    public void findByDescriptionCup() throws Exception {

        // given
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByUnit("Cup");

        // then
        assertEquals("Cup", unitOfMeasureOptional.get().getUnit());
    }
}