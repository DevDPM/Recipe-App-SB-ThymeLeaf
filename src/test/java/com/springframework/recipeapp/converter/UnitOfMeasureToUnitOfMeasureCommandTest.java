package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.UnitOfMeasureCommand;
import com.springframework.recipeapp.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    public final Long LONG_ID = 1L;
    public final String DESCRIPTION = "description";

    UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        toUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void convert() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_ID);
        uom.setUnit(DESCRIPTION);

        UnitOfMeasureCommand uomCommand = toUnitOfMeasureCommand.convert(uom);

        assertNotNull(uom);
        assertEquals(LONG_ID, uomCommand.getId());
        assertEquals(DESCRIPTION, uomCommand.getUnit());
    }

    @Test
    public void testNullParameter() {
        assertNull(toUnitOfMeasureCommand.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(toUnitOfMeasureCommand.convert(new UnitOfMeasure()));
    }
}