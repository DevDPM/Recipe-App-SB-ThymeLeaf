package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.UnitOfMeasureCommand;
import com.springframework.recipeapp.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    public final String DESCRIPTION = "description";
    public final Long LONG_VALUE = 5L;

    UnitOfMeasureCommandToUnitOfMeasure conversion;

    @BeforeEach
    void setUp() {
        conversion = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameter() {
        assertNull(conversion.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(conversion.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void convert() {
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(LONG_VALUE);
        command.setUnit(DESCRIPTION);

        UnitOfMeasure uom = conversion.convert(command);

        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getUnit());
    }
}