package com.springframework.recipeapp.converter;

import com.springframework.recipeapp.command.UnitOfMeasureCommand;
import com.springframework.recipeapp.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source == null) {
            return null;
        }

        final UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
        uom.setId(source.getId());
        uom.setUnit(source.getUnit());
        return uom;
    }
}
